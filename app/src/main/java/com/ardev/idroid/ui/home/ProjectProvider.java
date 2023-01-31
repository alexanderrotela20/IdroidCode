package com.ardev.idroid.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import java.io.File;
import com.ardev.builder.project.Project;


public class ProjectProvider {

    private static Project project;

    public static void init(@NonNull Project project) {
        this.project = project;
    }

    public static Project getProject() {
        if (project == null) {
            throw new IllegalStateException("init() has not been called yet in ProjectManager.class ");
        }
        return project;

    }
}
