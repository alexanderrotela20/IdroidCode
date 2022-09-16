package com.ardev.builder.compiler;

import com.ardev.builder.exception.CompilationFailedException;
import com.ardev.builder.project.Project;
import com.ardev.builder.project.api.Module;

import java.io.IOException;

/**
 *
 */
public abstract class Task<T extends Module> {

    private final T mProject;

    public Task(T project) {
        mProject = project;
        
    }

   
    protected T getModule() {
        return mProject;
    }

    
    public abstract String getName();

    
    public abstract void prepare(BuildType type) throws IOException;

    
    public abstract void run() throws IOException, CompilationFailedException;

    
    protected void clean() {

    }
}
