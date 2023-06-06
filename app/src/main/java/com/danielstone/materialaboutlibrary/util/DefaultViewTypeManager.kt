package com.danielstone.materialaboutlibrary.util

import android.content.Context
import android.view.View
import com.danielstone.materialaboutlibrary.holders.MaterialAboutItemViewHolder
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem

class DefaultViewTypeManager : ViewTypeManager() {

    object ItemType {
        const val ACTION_ITEM = ViewTypeManager.ItemType.ACTION_ITEM
        const val TITLE_ITEM = ViewTypeManager.ItemType.TITLE_ITEM
    }

    object ItemLayout {
        const val ACTION_LAYOUT = ViewTypeManager.ItemLayout.ACTION_LAYOUT
        const val TITLE_LAYOUT = ViewTypeManager.ItemLayout.TITLE_LAYOUT
    }

    override fun getLayout(itemType: Int): Int {
        return when (itemType) {
            ItemType.ACTION_ITEM -> ItemLayout.ACTION_LAYOUT
            ItemType.TITLE_ITEM -> ItemLayout.TITLE_LAYOUT
            else -> -1
        }
    }

    override fun getViewHolder(itemType: Int, view: View): MaterialAboutItemViewHolder {
        return when (itemType) {
            ItemType.ACTION_ITEM -> MaterialAboutActionItem.getViewHolder(view)
            ItemType.TITLE_ITEM -> MaterialAboutTitleItem.getViewHolder(view)
            else -> null
        }
    }

    override fun setupItem(itemType: Int, holder: MaterialAboutItemViewHolder, item: MaterialAboutItem, context: Context) {
        when (itemType) {
            ItemType.ACTION_ITEM -> MaterialAboutActionItem.setupItem(holder as MaterialAboutActionItem.MaterialAboutActionItemViewHolder, item as MaterialAboutActionItem, context)
            ItemType.TITLE_ITEM -> MaterialAboutTitleItem.setupItem(holder as MaterialAboutTitleItem.MaterialAboutTitleItemViewHolder, item as MaterialAboutTitleItem, context)
        }
    }
}
