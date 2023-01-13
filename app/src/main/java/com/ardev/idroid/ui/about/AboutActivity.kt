package com.ardev.idroid.ui.about

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import com.ardev.idroid.R
import com.ardev.idroid.ext.addSystemWindowInsetToPadding

class AboutActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
       val toolbar = findViewById<Toolbar>(R.id.toolbar)
           
        setSupportActionBar(toolbar)
       window.decorView.addSystemWindowInsetToPadding( false, false, false, false)
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
       supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setTitle("Acerca de")
        toolbar.setNavigationOnClickListener {
        onBackPressed()
}
        
        
    }
   
}

 

