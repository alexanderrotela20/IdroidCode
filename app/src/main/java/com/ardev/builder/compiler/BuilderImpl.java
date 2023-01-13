package com.ardev.builder.compiler;

import android.os.Handler;
import android.os.Looper;

import com.ardev.builder.exception.CompilationFailedException;
import com.ardev.builder.project.api.Module;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BuilderImpl<T extends Module> implements Builder<T> {

    private final Handler mMainHandler;
    private final T mProject;
    
    private final List<Task<? super T>> mTasksRan;
    private TaskListener mTaskListener;

    public BuilderImpl(T project) {
        mProject = project;
      
        mMainHandler = new Handler(Looper.getMainLooper());
        mTasksRan = new ArrayList<>();
    }

    @Override
    public void setTaskListener(TaskListener taskListener) {
        mTaskListener = taskListener;
    }

    @Override
    public T getModule() {
        return mProject;
    }

    protected void updateProgress(String name, String message, int progress) {
        if (mTaskListener != null) {
            mTaskListener.onTaskStarted(name, message, progress);
        }
    }

    @Override
    public final void build(BuildType type) throws CompilationFailedException, IOException {
        mTasksRan.clear();
        List<Task<? super T>> tasks = getTasks(type);
        for (int i = 0, tasksSize = tasks.size(); i < tasksSize; i++) {
            Task<? super T> task = tasks.get(i);
            final float current = i;
            
            try {
                mMainHandler.post(() -> updateProgress(task.getName(), "Task started",
                        (int) ((current / (float) tasks.size()) * 100f)));
                task.prepare(type);
                task.run();
            } catch (Throwable e) {
                if (e instanceof OutOfMemoryError) {
                    tasks.clear();
                    mTasksRan.clear();
                    throw new CompilationFailedException("Builder ran out of memory", e);
                }
                task.clean();
                mTasksRan.forEach(Task::clean);
                throw e;
            }
            mTasksRan.add(task);
        }
        mTasksRan.forEach(Task::clean);
    }

    public abstract List<Task<? super T>> getTasks(BuildType type);

   
   
    public List<Task<? super T>> getTasksRan() {
        return mTasksRan;
    }

   
}
