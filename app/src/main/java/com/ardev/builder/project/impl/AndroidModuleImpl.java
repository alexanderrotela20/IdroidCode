package com.ardev.builder.project.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.charset.Charset;
import com.ardev.builder.project.api.AndroidModule;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import com.ardev.builder.common.util.StringSearch;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class AndroidModuleImpl extends JavaModuleImpl implements AndroidModule {

    
    private final Map<String, File> mKotlinFiles;
    private Map<String, File> mResourceClasses;

    public AndroidModuleImpl(File root) {
        super(root);

        mKotlinFiles = new HashMap<>();
        mResourceClasses = new HashMap<>(1);
    }

    @Override
    public void open() throws IOException {
        super.open();
        
    }

    @Override
    public void index() {
        super.index();

        Consumer<File> kotlinConsumer = this::addKotlinFile;

        if (getJavaDirectory().exists()) {
            FileUtils.iterateFiles(getJavaDirectory(),
                    FileFilterUtils.suffixFileFilter(".kt"),
                    TrueFileFilter.INSTANCE
            ).forEachRemaining(kotlinConsumer);
        }

        if (getKotlinDirectory().exists()) {
            FileUtils.iterateFiles(getKotlinDirectory(),
                    FileFilterUtils.suffixFileFilter(".kt"),
                    TrueFileFilter.INSTANCE
            ).forEachRemaining(kotlinConsumer);
        }
		
		try {
        
        String content  = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(getAllClasses(),new TypeToken<Set<String>>(){}.getType());
                
        
         File clases = new File(getBuildDirectory(), "Lista de clases.json");
         if(!clases.exists()) clases.createNewFile();
	
        FileUtils.writeStringToFile(clases,
                 content,
                 Charset.defaultCharset());
                 
                 } catch (Exception e) {}
        
        
    }

    @Override
    public File getAndroidResourcesDirectory() {
      
        return new File(getRootFile(), "src/main/res");
    }

    @Override
    public Set<String> getAllClasses() {
        Set<String> classes = super.getAllClasses();
        classes.addAll(mKotlinFiles.keySet());
        return classes;
    }

    @Override
    public File getNativeLibrariesDirectory() {
      
        return new File(getRootFile(), "src/main/jniLibs");
    }

    @Override
    public File getAssetsDirectory() {
       
        return new File(getRootFile(), "src/main/assets");
    }

    @Override
    public String getPackageName() {
       
      
        return null;
    }

    @Override
    public File getManifestFile() {
       
      return new File(getRootFile(), "src/main/AndroidManifest.xml");
    }

    @Override
    public int getTargetSdk() {
        return 30;
    }

    @Override
    public int getMinSdk() {
        return 21;
    }

    @Override
    public void addResourceClass(@NonNull File file) {
        mResourceClasses.put(StringSearch.packageName(file), file);
    }

    @Override
    public Map<String, File> getResourceClasses() {
        return ImmutableMap.copyOf(mResourceClasses);
    }

    @NonNull
    @Override
    public Map<String, File> getKotlinFiles() {
        return ImmutableMap.copyOf(mKotlinFiles);
    }

    @NonNull
    @Override
    public File getKotlinDirectory() {
        return new File(getRootFile(), "src/main/kotlin");
    }

    @Nullable
    @Override
    public File getKotlinFile(String packageName) {
        return mKotlinFiles.get(packageName);
    }

    @Override
    public void addKotlinFile(File file) {
        String packageName = StringSearch.packageName(file);
        if (packageName == null) {
            packageName = "";
        }
        String fqn = packageName + "." + file.getName().replace(".kt", "");
        mKotlinFiles.put(fqn, file);
    }

    @Override
    public void clear() {
        super.clear();

        try {
          
        } catch (Throwable e) {
            throw new Error(e);
        }
    }
}
