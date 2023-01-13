package com.ardev.idroid.app

import android.content.Context
import android.os.Bundle
import android.content.SharedPreferences
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ardev.idroid.app.IdroidApplication
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import com.ardev.idroid.app.theme.DarkThemeHelper
import com.ardev.idroid.ext.addSystemWindowInsetToPadding

abstract class AppActivity: AppCompatActivity() {


    lateinit var settings: SharedPreferences

	private var isDelegateCreated: Boolean = false
	
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
    WindowCompat.setDecorFitsSystemWindows(getWindow(), false)
        getRootView().addSystemWindowInsetToPadding(false, false, false, false)
		settings = IdroidApplication.getDefaultSharedPreferences()

    }


	override fun getDelegate(): AppCompatDelegate {
        if (!isDelegateCreated) {
            isDelegateCreated = true
            DarkThemeHelper.apply(this)
        }
        return super.getDelegate()
    }

    private fun getRootView(): View = getWindow().getDecorView().findViewById(android.R.id.content)
    
}