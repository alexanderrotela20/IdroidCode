package com.ardev.idroid.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import java.io.File;
import com.ardev.builder.project.Project;

/**
 * Utility class to retrieve the application context from anywhere.
 */
public class ProjectProvider {

    private static File root;

    public static void init(@NonNull Project project) {
        root = project.getRootFile();
    }

    public static File getRootFile() {
        if (root == null) {
            throw new IllegalStateException("init() has not been called yet in ProjectManager.class ");
        }
        return root;
    }
}
