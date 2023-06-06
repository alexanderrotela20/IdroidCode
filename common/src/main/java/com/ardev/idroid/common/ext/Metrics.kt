package com.ardev.idroid.common.ext

import android.content.res.Resources
import android.util.TypedValue
import com.ardev.idroid.app.IdroidApplication.context

val Float.dp get() = Math.round(context.resources.displayMetrics.density * this)

val Float.sp get() = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_SP, this, context.resources.displayMetrics ) 

val Int.dp get() = toFloat().dp
val Int.sp get() = toFloat().sp


val Int.rowCount get() = context.resources.displayMetrics.widthPixels / this

val statusBarHeight: Int get() {
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
}

val navBarHeight: Int get() {
    val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
}