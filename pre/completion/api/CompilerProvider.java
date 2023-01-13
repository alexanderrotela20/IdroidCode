package com.ardev.builder.completion.api;

import com.ardev.builder.project.Project;
import com.ardev.builder.project.api.Module;

public abstract class CompilerProvider<T> {

    public abstract T get(Project project, Module module);
}
