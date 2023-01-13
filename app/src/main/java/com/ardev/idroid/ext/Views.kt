package com.ardev.idroid.ext

import android.widget.TextView
import android.widget.Toast
import android.view.*
import com.google.android.material.snackbar.Snackbar
import com.ardev.idroid.app.IdroidApplication

fun TextView.setTextIfDifferent(data: String) {
    if (this.text.toString() != data) {
        text = data
    }

}

fun String.showToast() =
    Toast.makeText(IdroidApplication.appContext(), this, Toast.LENGTH_SHORT).show()

fun String.showSnackBar(view: View) =
    Snackbar.make(view, this, Snackbar.LENGTH_LONG)
        .apply {
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
        }.show()
