package com.ardev.idroid.app;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import com.ardev.idroid.ui.main.MainViewModel;
import androidx.annotation.NonNull;
import java.lang.ref.WeakReference;

/**
 * Utility class to retrieve the application context from anywhere.
 */
public class MainViewModelProvider {
	private MainViewModel viewModel;
    private static Context mContext;
	private  static WeakReference<MainViewModel> ref;
    public static void init(@NonNull MainViewModel viewModel) {
        		ref = new WeakReference<>(viewModel);
    }

    public static MainViewModel getViewModel() {
        if (ref.get() == null) {
            throw new IllegalStateException("init() has not been called yet in ViewModelProvider.class ");
        }
        return ref.get();
    }
}
