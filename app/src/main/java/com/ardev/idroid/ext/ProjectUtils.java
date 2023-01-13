package com.ardev.idroid.ext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.io.File;

public class ProjectUtils {

    /**
     * Utility method to get package name from a current directory, this traverses up
     * the file hierarchy until it reaches the "java" folder we can then workout the possible
     * package name from that
     *
     * @param directory the parent file of the class
     * @return null if name cannot be determined
     */
    @Nullable
    public static String getPackageName(File directory) {
        if (!directory.isDirectory()) {
            return null;
        }

        File original = directory;

        while (!isJavaFolder(directory)) {
            if (directory == null) {
                return null;
            }
            directory = directory.getParentFile();
        }

        String originalPath = original.getAbsolutePath();
        String javaPath = directory.getAbsolutePath();

        String cutPath = originalPath.replace(javaPath, "");
        return formatPackageName(cutPath);
    }
    
    /**
     * Formats a path into a package name
     * eg. com/my/test into com.my.test
     *
     * @param path input path
     * @return formatted package name
     */
    private static String formatPackageName(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        return path.replace("/", ".");
    }


    /**
     * Utility method to determine if the folder is the app/src/main/java folder
     *
     * @param file file to check
     * @return true if its the java folder
     */
    private static boolean isJavaFolder(File file) {
        if (file == null) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }

        if (file.getName().equals("java")) {
            File parent = file.getParentFile();
            if (parent == null) {
                return false;
            } else return parent.getName().equals("main");
        }

        return false;
    }

public static boolean isJavaFile(@NonNull File file) {
        return file.getName().endsWith(".java");
    }



public static boolean isXMLFile(@NonNull File file) {
        return file.getName().endsWith(".xml");
    }

    public static boolean isResourceXMLDir(File dir) {
        if (dir == null) {
            return false;
        }
        File parent = dir.getParentFile();
        if (parent != null) {
            return parent.getName().equals("res");
        }
        return false;
    }

    public static boolean isResourceXMLFile(@NonNull File file) {
        if (!file.getName().endsWith(".xml")) {
            return false;
        }
        return isResourceXMLDir(file.getParentFile());
    }
    
    public static boolean isDrawableXMLFile(@NonNull File file) {
        if (!file.getName().endsWith(".xml")) {
            return false;
        }

        if (file.getParentFile() != null) {
            File parent = file.getParentFile();
            if (parent.isDirectory() && parent.getName().startsWith("drawable")) {
                return isResourceXMLFile(file);
            }
        }

        return false;
    }

    public static boolean isLayoutXMLFile(@NonNull File file) {
        if (!file.getName().endsWith(".xml")) {
            return false;
        }

        if (file.getParentFile() != null) {
            File parent = file.getParentFile();
            if (parent.isDirectory() && parent.getName().startsWith("layout")) {
                return isResourceXMLFile(file);
            }
        }

        return false;
    }
    
    public static boolean isValuesXMLFile(@NonNull File file) {
        if (!file.getName().endsWith(".xml")) {
            return false;
        }

        if (file.getParentFile() != null) {
            File parent = file.getParentFile();
            if (parent.isDirectory() && parent.getName().startsWith("values")) {
                return isResourceXMLFile(file);
            }
        }

        return false;
    }
    
    
    
}
