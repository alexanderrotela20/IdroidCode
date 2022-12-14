package com.ardev.idroid.ext

import android.app.Activity
import androidx.annotation.StyleRes
import androidx.core.app.ActivityCompat

fun Activity.recreateCompat() {
    ActivityCompat.recreate(this)
}

fun Activity.setThemeCompat(@StyleRes resid: Int) {
    setTheme(resid)
}
