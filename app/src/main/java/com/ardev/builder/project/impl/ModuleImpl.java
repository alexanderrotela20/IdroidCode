package com.ardev.builder.project.impl;

import androidx.annotation.Nullable;

import com.ardev.builder.project.api.FileManager;
import com.ardev.builder.project.api.Module;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModuleImpl implements Module {

    /**
     * Concurrent writes to this field are via CASes only, using the {@link #updater}
     */
    
    private final File mRoot;
    
    private FileManager mFileManager;

    public ModuleImpl(File root) {
        mRoot = root;
        mFileManager = new FileManagerImpl(root);
    }

    @Override
    public void open() throws IOException {
        if(!getBuildDirectory().exists()) getBuildDirectory().mkdir();
        if(!getCacheDirectory().exists()) getCacheDirectory().mkdir();
        
    }

    @Override
    public void clear() {

    }

    @Override
    public void index() {

    }

    @Override
    public File getBuildDirectory() {
    return new File(getRootFile(), "build");
   
    }
	
	@Override
    public File getCacheDirectory() {
    return new File(getBuildDirectory(), ".cache");
    }

  

    @Override
    public FileManager getFileManager() {
        return mFileManager;
    }

    @Override
    public File getRootFile() {
        return mRoot;
    }

    
    

    @Override
    public int hashCode() {
        return mRoot.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModuleImpl)) return false;
        ModuleImpl project = (ModuleImpl) o;
        return mRoot.equals(project.mRoot);
    }

   
}
