package com.ardev.idroid.app.task;

import java.util.ArrayDeque;
import java.util.Queue;

public class StartupManager {

    private ArrayDeque<Runnable> mStartupActions;

    public StartupManager() {
        mStartupActions = new ArrayDeque<>();
    }

    public void addStartupAction(Runnable runnable) {
        mStartupActions.add(runnable);
    }

    public void startup() {
        Runnable runnable = mStartupActions.remove();
        while (runnable != null) {
            runnable.run();
            if (!mStartupActions.isEmpty()) {
                runnable = mStartupActions.remove();
            } else {
                runnable = null;
            }
        }
    }
}
