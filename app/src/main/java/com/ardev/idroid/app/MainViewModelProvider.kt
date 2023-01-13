package com.ardev.idroid.app

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ardev.idroid.ui.main.MainViewModel;
import java.lang.ref.WeakReference

object MainViewModelProvider {
    private lateinit var viewModel: MainViewModel
    private lateinit var ref: WeakReference<MainViewModel>

    fun init(viewModel: MainViewModel) {
        ref = WeakReference(viewModel)
    }

    fun getViewModel(): MainViewModel {
        if (ref.get() == null) {
            throw IllegalStateException("init () no se ha llamado todavía en ViewModelProvider.class")
        }

        return ref.get()!!
    }
}

