package com.ardev.idroid.ui

import android.os.Bundle
import android.os.Handler
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.ardev.idroid.ui.home.HomeActivity
import com.ardev.idroid.app.AppActivity

class SplashActivity : AppActivity() {

	override fun onCreate(savedInstanceState : Bundle? ) {
		super.onCreate(savedInstanceState)
		cambiarActivity(this, HomeActivity::class.java)
		
	}
    
    
  
}
