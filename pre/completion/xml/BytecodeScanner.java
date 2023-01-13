package com.ardev.builder.completion.xml;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.ImmutableSet;
import com.ardev.builder.BuildModule;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Scans jar files and saves all the class files that extends {@link View} and has the
 * appropriate constructors to be inflated in XML.
 */
public class BytecodeScanner {

    private static final Set<String> sIgnoredPaths;

    static {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        builder.add("android");
        builder.add("android/util");
        builder.add("android/os");
        builder.add("android/os/health");
        builder.add("android/os/strictmode");
        builder.add("android/os/storage");
        builder.add("android/graphics");
        builder.add("android/graphics/drawable");
        builder.add("android/graphics/fonts");
        builder.add("android/graphics/pdf");
        builder.add("android/graphics/text");
        builder.add("android/system");
        builder.add("android/content");
        builder.add("android/content/res");
        builder.add("android/content/pm");
        sIgnoredPaths = builder.build();
    }

    public static void loadJar(File jar) throws IOException {
        try (JarFile jarFile = new JarFile(jar)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry element = entries.nextElement();
                if (!element.getName().endsWith(".class")) {
                    continue;
                }
                ClassParser classParser = new ClassParser(jar.getAbsolutePath(), element.getName());
                JavaClass parse = classParser.parse();
                Repository.addClass(parse);
            }
        }
    }

    public static List<JavaClass> scan(File file) throws IOException {
        String path = file.getAbsolutePath();
        List<JavaClass> viewClasses = new ArrayList<>();
        try (JarFile jarFile = new JarFile(file)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry element = entries.nextElement();
                if (!element.getName().endsWith(".class")) {
                    continue;
                }
                ClassParser classParser = new ClassParser(path, element.getName());
                JavaClass parse = classParser.parse();
                if (isViewClass(parse)) {
                    viewClasses.add(parse);
                }
            }
        }
        return viewClasses;
    }

    public static boolean isViewGroup(JavaClass javaClass) {
        JavaClass[] superClasses;
        try {
            superClasses = javaClass.getSuperClasses();
        } catch (ClassNotFoundException e) {
            return false;
        }
        for (JavaClass superClass : superClasses) {
            if (ViewGroup.class.getName().equals(superClass.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void scanBootstrapIfNeeded() {
        if (!needScanBootstrap()) {
            return;
        }

        File androidJar = BuildModule.getAndroidJar();
        if (androidJar != null && androidJar.exists()) {
            try (JarFile jarFile = new JarFile(androidJar)) {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry element = entries.nextElement();
                    String name = element.getName();
                    if (!name.endsWith(".class")) {
                        continue;
                    }
                    String packagePath = name.substring(0, name.lastIndexOf('/'));
                    if (sIgnoredPaths.contains(packagePath)) {
                        continue;
                    }
                    if (packagePath.startsWith("java/")) {
                        continue;
                    }
                    ClassParser classParser = new ClassParser(androidJar.getAbsolutePath(), name);
                    JavaClass parse = classParser.parse();
                    Repository.addClass(parse);
                }
            } catch (IOException e) {
                // ignored
            }
        }
    }

    private static boolean needScanBootstrap() {
        try {
            Repository.getRepository().loadClass(View.class);
            return false;
        } catch (ClassNotFoundException e) {
            return true;
        }
    }

    private static boolean isViewClass(JavaClass javaClass) {
        try {
            JavaClass[] superClasses = javaClass.getSuperClasses();
            for (JavaClass superClass : superClasses) {
                if (View.class.getName().equals(superClass.getClassName())) {
                    Method[] methods = javaClass.getMethods();
                    if (containsViewConstructors(methods)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean containsViewConstructors(Method[] methods) {
        for (Method method : methods) {
            if (!"<init>".equals(method.getName())) {
                continue;
            }

            Type[] argumentTypes = method.getArgumentTypes();
            if (argumentTypes.length == 2) {
                if (Context.class.getName().equals(argumentTypes[0].toString())) {
                    if (AttributeSet.class.getName().equals(argumentTypes[1].toString())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
