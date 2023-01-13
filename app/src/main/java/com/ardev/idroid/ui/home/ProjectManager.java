package com.ardev.idroid.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ardev.builder.compiler.BuildType;
import com.ardev.builder.exception.CompilationFailedException;
import com.ardev.builder.project.Project;
import com.ardev.builder.project.api.AndroidModule;
import com.ardev.builder.project.api.JavaModule;
import com.ardev.builder.project.api.Module;
import com.ardev.idroid.ext.CoroutineUtil;
import android.os.Handler;
import android.os.Looper;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

public class ProjectManager {
private final Handler mMainHandler = new Handler(Looper.getMainLooper());
    public interface TaskListener {
        void onTaskStarted(String message);

        void onComplete(Project project, boolean success, String message);
    }

    public interface OnProjectOpenListener {
        void onProjectOpen(Project project);
    }

    private static volatile ProjectManager INSTANCE = null;

    public static synchronized ProjectManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProjectManager();
        }
        return INSTANCE;
    }

    private final List<OnProjectOpenListener> mProjectOpenListeners = new ArrayList<>();
    private volatile Project mCurrentProject;

    private ProjectManager() {

    }

    public void addOnProjectOpenListener(OnProjectOpenListener listener) {
        if ( mCurrentProject != null) {
            listener.onProjectOpen(mCurrentProject);
        }
        mProjectOpenListeners.add(listener);
    }

    public void removeOnProjectOpenListener(OnProjectOpenListener listener) {
        mProjectOpenListeners.remove(listener);
    }

    public void openProject(Project project,
                            boolean downloadLibs,
                            TaskListener listener) {
  CoroutineUtil.inParallel(() -> doOpenProject(project, downloadLibs, listener) );
    }

    private void doOpenProject(Project project,
                               boolean downloadLibs,
                               TaskListener mListener) {
        mCurrentProject = project;

        boolean shouldReturn = false;
        // Index the project after downloading dependencies so it will get added to classpath
        try {
			
          mCurrentProject.open();
        } catch (Exception exception) {
            
            shouldReturn = true;
        }
        mProjectOpenListeners.forEach(it -> it.onProjectOpen(mCurrentProject));

        if (shouldReturn) {
            mListener.onComplete(project, false, "Failed to open project.");
            return;
        }

        try {
        
        
           mCurrentProject.index();
        } catch (Exception exception) {
           
        }

        Module module = mCurrentProject.getMainModule();

        if (module instanceof JavaModule) {
            JavaModule javaModule = (JavaModule) module;
			
            try {
               mListener.onTaskStarted("Downloading files...");
            } catch (Exception e) {
               
            }
        }


        if (module instanceof AndroidModule) {
            mListener.onTaskStarted("Generating resource files.");

            
            try {
              
            } catch ( Exception e) {
     
            }
        }
        if (module instanceof JavaModule) {
            if (module instanceof AndroidModule) {
                mListener.onTaskStarted("Indexing XML files.");
		
             // XmlIndexProvider index = CompilerService.getInstance()
                       // .getIndex(XmlIndexProvider.KEY);
               /// index.clear();

              //  XmlRepository xmlRepository = index.get(project, module);
           //     try {
                  //  xmlRepository.initialize((AndroidModule) module);
           //     } catch (IOException e) {
                    // ignored
             //   }
            }

            mListener.onTaskStarted("Indexing");
            try {
               

                if (module instanceof AndroidModule) {
                   
                }
            } catch (Throwable e) {
               
                
            }
        }
        mMainHandler.post( () -> {
		new Handler().postDelayed(new Runnable(){

                @Override
                public void run() {
                  mListener.onComplete(project, true, "Index successful");
        
                }
            }, 5000);
     
        });
    }

    

    public void closeProject(@NonNull Project project) {
        if (project.equals(mCurrentProject)) {
            mCurrentProject = null;
        }
    }

    public synchronized Project getCurrentProject() {
        return mCurrentProject;
    }

    
}
