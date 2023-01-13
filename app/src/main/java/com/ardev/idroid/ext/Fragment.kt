package com.ardev.idroid.ext

import androidx.fragment.app.Fragment

fun Fragment.isAlive(): Boolean {
    return activity != null && !isDetached && isAdded && !isRemoving && view != null
}