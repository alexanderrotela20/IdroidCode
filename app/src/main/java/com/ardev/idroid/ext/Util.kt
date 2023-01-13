package com.ardev.idroid.ext

import android.widget.TextView
import android.content.Context
import android.content.Intent

import android.view.View
import android.view.ViewGroup
import androidx.core.view.*


fun View.setMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    val params = (layoutParams as? ViewGroup.MarginLayoutParams)
    params?.setMargins(
        left ?: params.leftMargin,
        top ?: params.topMargin,
        right ?: params.rightMargin,
        bottom ?: params.bottomMargin)
    layoutParams = params
}

@JvmOverloads
fun View.addSystemWindowInsetToPadding(
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false
) {
    val (initialLeft, initialTop, initialRight, initialBottom) =
        listOf(paddingLeft, paddingTop, paddingRight, paddingBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updatePadding(
            left = initialLeft + (if (left) insets.left else 0),
            top = initialTop + (if (top) insets.top else 0),
            right = initialRight + (if (right) insets.right else 0),
            bottom = initialBottom + (if (bottom) insets.bottom else 0)
        )
        windowInsets
    }
}

@JvmOverloads
fun View.addSystemWindowInsetToMargin(
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false
) {
    val (initialLeft, initialTop, initialRight, initialBottom) =
        listOf(marginLeft, marginTop, marginRight, marginBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) {  view, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            updateMargins(
                left = initialLeft + (if (left) insets.left else 0),
                top = initialTop + (if (top) insets.top else 0),
                right = initialRight + (if (right) insets.right else 0),
                bottom = initialBottom + (if (bottom) insets.bottom else 0)
            )
        }
        windowInsets
    }
}
@JvmOverloads
fun View.addInsetTypeImeToPadding() {
ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets -> 
                    val imeVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime())
                    val insets  = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                   view.setPadding(0, 0, 0, insets.bottom)
                    if (imeVisible) {
                        val imeHeight = windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                       view.setPadding(0, 0, 0, imeHeight)
                    }
                    windowInsets
        }

}