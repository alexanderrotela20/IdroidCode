package com.ardev.idroid.ui

import android.os.Bundle
import android.os.Handler
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.ardev.idroid.ui.home.HomeActivity
import com.ardev.idroid.base.BasePermissionActivity

class SplashActivity : BasePermissionActivity() {

	override fun onCreate(savedInstanceState : Bundle? ) {
		super.onCreate(savedInstanceState)
		
	}
    
    
    
    override fun onPermissionsGranted() {
  
  //	Handler.postDelayed( () -> {
               cambiarActivity(this, HomeActivity::class.java)
      //      }, 500)
  
  
  }
  
    
    
}
