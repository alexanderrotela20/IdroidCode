package com.danielstone.materialaboutlibrary.util

import android.content.Context
import android.view.View
import com.ardev.idroid.R
import com.danielstone.materialaboutlibrary.holders.MaterialAboutItemViewHolder
import com.danielstone.materialaboutlibrary.items.MaterialAboutItem

abstract class ViewTypeManager {

    object ItemType {
        const val ACTION_ITEM = 0
        const val TITLE_ITEM = 1
    }

    object ItemLayout {
        const val ACTION_LAYOUT = R.layout.mal_material_about_action_item
        const val TITLE_LAYOUT = R.layout.mal_material_about_title_item
    }

    abstract fun getLayout(itemType: Int): Int

    abstract fun getViewHolder(itemType: Int, view: View): MaterialAboutItemViewHolder

    abstract fun setupItem(itemType: Int, holder: MaterialAboutItemViewHolder, item: MaterialAboutItem, context: Context)
}
