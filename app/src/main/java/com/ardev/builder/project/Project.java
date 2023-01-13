package com.ardev.builder.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ardev.builder.project.api.Module;
import com.ardev.builder.project.impl.AndroidModuleImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;


public class Project {
 	private final Module mMainModule;
    private final File mRoot;

    

    private volatile boolean mCompiling;

  
    
    public Project(File root) {
        mRoot = root;
      mMainModule = new AndroidModuleImpl(new File(mRoot, "app"));
       
    }

    public boolean isCompiling() {
        return mCompiling;
    }

    public void setCompiling(boolean compiling) {
        mCompiling = compiling;
    }

   

    @NonNull
    public Module getMainModule() {
        return mMainModule;
    }
    
	public void addModule( File library) {
		
		}

    public File getRootFile() {
        return mRoot;
    }
	
	public void open() throws IOException {
        mMainModule.open();
	}
	
	public void index() throws IOException {
        
            mMainModule.clear();
            mMainModule.index();
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return mRoot.equals(project.mRoot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mRoot);
    }


}
