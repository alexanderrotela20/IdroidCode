package com.ardev.idroid.base

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ardev.idroid.app.IdroidApplication
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import com.ardev.idroid.app.AppActivity
import com.ardev.idroid.app.theme.DarkThemeHelper
import com.ardev.idroid.ext.addSystemWindowInsetToPadding

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppActivity() {

	private lateinit var binding: VB
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         if (!this::binding.isInitialized) {
            binding = getViewBinding()
        }
        setContentView(binding.root)
		

        observeViewModel()

    }

    protected fun observeViewModel() {}
   protected abstract fun getViewBinding(): VB


}