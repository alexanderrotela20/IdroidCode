package com.ardev.idroid.ui.about

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danielstone.materialaboutlibrary.MaterialAboutFragment
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.google.android.material.transition.MaterialSharedAxis
import com.ardev.idroid.R

class AboutFragment : MaterialAboutFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
    }

    override fun getTitle(): String = getString(R.string.about)

    override fun getMaterialAboutList(context: Context): MaterialAboutList {
        return createAboutList(requireContext())
    }

    companion object {
        fun newInstance(): AboutFragment = AboutFragment()
    }
}