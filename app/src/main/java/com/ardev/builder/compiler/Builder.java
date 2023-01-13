package com.ardev.builder.compiler;

import androidx.annotation.MainThread;



import com.ardev.builder.exception.CompilationFailedException;
import com.ardev.builder.project.api.Module;
import java.io.IOException;
import java.util.List;

public interface Builder<T extends Module> {

    interface TaskListener {
        @MainThread
        void onTaskStarted(String name, String message, int progress);
    }

    void setTaskListener(TaskListener taskListener);

    T getModule();

    void build(BuildType type) throws CompilationFailedException, IOException;

    

    List<Task<? super T>> getTasks(BuildType type);
}
