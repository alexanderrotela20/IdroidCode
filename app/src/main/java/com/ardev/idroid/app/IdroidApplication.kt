package com.ardev.idroid.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.view.Window
import android.content.Intent
import android.app.PendingIntent
import android.app.AlarmManager

import com.ardev.tools.parser.proxy.ProxyResources
import com.ardev.idroid.app.theme.DarkThemeHelper
import java.io.StringWriter
import java.io.Writer
import java.io.PrintWriter
import androidx.appcompat.app.AppCompatDelegate
import com.ardev.idroid.ext.CoroutineUtil
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class IdroidApplication: Application() {

 
		init {
        instance = this
    		}


	override fun onCreate() {
			super.onCreate()
	
      CoroutineUtil.inParallel {
			//DynamicColors.applyToActivitiesIfAvailable(this)
            DarkThemeHelper.initialize(this)
			AppProvider.init(applicationContext)
			ProxyResources.init(applicationContext)
			
            // val index: CompilerService = CompilerService.getInstance()
            // if (index.isEmpty()) {
                // index.registerIndexProvider(XmlIndexProvider.KEY, XmlIndexProvider())
           		 // }
       
		}
	
		

	}

 
companion object {
        public var instance: IdroidApplication? = null
		@JvmStatic
		fun getApp(): Application = instance!!
		 @JvmStatic
        fun appContext(): Context = instance!!.applicationContext
 

        @JvmStatic
        fun getDefaultSharedPreferences(): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(appContext())
        }
        @JvmStatic
        fun isDarkMode(context: Context): Boolean {
            val darkModeFlag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
        }
}

}
