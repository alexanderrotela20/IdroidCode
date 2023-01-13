package com.ardev.component.view.colorpicker.listeners

/**
 * Empty implementation of [OnColorSelectionListener] so that the clients can override only the methods they care
 */
open class SimpleColorSelectionListener : OnColorSelectionListener {

    override fun onColorSelected(color: Int) {}

    override fun onColorSelectionStart(color: Int) {}

    override fun onColorSelectionEnd(color: Int) {}
}