package com.ardev.idroid.ext

fun String.endsWith(vararg prefix: String): Boolean {
    for (it in prefix) {
       if (this.endsWith(it, false)) return true
    }
    return false
}