package com.ardev.idroid.ui.about

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.ardev.idroid.R
import com.ardev.idroid.common.ext.*

    fun Fragment.createAboutList(): MaterialAboutList {
    val appCard = MaterialAboutCard.Builder()
        .addItem(
            MaterialAboutTitleItem.Builder()
                .text(context.getString(R.string.app_name))
                .desc(context.getString(R.string.about_desc))
                .icon(R.drawable.logo)
                .build()
        )
        .addItem(
            ConvenienceBuilder.createVersionActionItem(
                context,
                context.getDrawableCompat(R.drawable.ic_info),
                context.getString(R.string.about_version),
                true
            )
        )
        .addItem(
            MaterialAboutActionItem.Builder()
                .text(context.getString(R.string.about_changelog_title))
                .icon(R.drawable.ic_changelog)
                .setOnClickAction(
                    
                )
                .build()
        )
        .addItem(
            MaterialAboutActionItem.Builder()
                .text(context.getString(R.string.about_license_title))
                .icon(R.drawable.ic_license)
                .setOnClickAction {
                    activity.launchActivity<OssLicensesMenuActivity>()
                }
                .build()
        )
        .outline(false)
        .build()

    val authorCard = MaterialAboutCard.Builder()
        .title(context.getString(R.string.about_author_title))
        .outline(false)
        .addItem(
            MaterialAboutActionItem.Builder()
                .text(context.getString(R.string.about_author_name))
                .subText(context.getString(R.string.about_author_location))
                .icon(R.drawable.ic_dev)
                .build()
        )
        .build()

    val communityCard = MaterialAboutCard.Builder()
        .title(context.getString(R.string.about_social_title))
        .outline(false)
        .addItem(
            ConvenienceBuilder.createWebsiteActionItem(
                context,
                context.getDrawableCompat(R.drawable.ic_facebook),
                context.getString(R.string.about_facebook_label),
                false,
                Uri.parse("https://facebook.com/alexander.rotela.18")
            )
        )
        .addItem(
            ConvenienceBuilder.createWebsiteActionItem(
                context,
                context.getDrawableCompat(R.drawable.ic_instagram),
                context.getString(R.string.about_instagram_label),
                false,
                Uri.parse("https://discord.gg/pffnyE6prs")
            )
        )
        .addItem(
            ConvenienceBuilder.createWebsiteActionItem(
                context,
                context.getDrawableCompat(R.drawable.ic_twitter),
                context.getString(R.string.about_twitter_label),
                false,
                Uri.parse("https://discord.gg/pffnyE6prs")
            )
        )
        .addItem(
            ConvenienceBuilder.createWebsiteActionItem(
                context,
                context.getDrawableCompat(R.drawable.ic_telegram),
                context.getString(R.string.about_telegram_label),
                false,
                Uri.parse("https://t.me/alexanderrotela")
            )
        )
        .build()

    val otherCard = MaterialAboutCard.Builder()
        .title(context.getString(R.string.about_others))
        .outline(false)
        .addItem(
            MaterialAboutActionItem.Builder()
                .icon(context.getDrawableCompat(R.drawable.ic_apoyar))
                .text(context.getString(R.string.about_support_title))
                .build()
        )
        .addItem(
            MaterialAboutActionItem.Builder()
                .icon(context.getDrawableCompat(R.drawable.ic_share))
                .text(context.getString(R.string.about_share_title))
                .build()
        )
        .addItem(
            ConvenienceBuilder.createEmailItem(
                context,
                context.getDrawableCompat(R.drawable.ic_coment),
                context.getString(R.string.about_feedback_title),
                false,
                context.getString(R.string.about_email_address),
                ""
            )
        )
        .build()

    val moreCard = MaterialAboutCard.Builder()
        .outline(false)
        .addItem(
            MaterialAboutActionItem.Builder()
                .icon(context.getDrawableCompat(R.drawable.ic_apps))
                .text(context.getString(R.string.about_more_apps_title))
                .build()
        )
        .build()

    return MaterialAboutList.Builder()
        .addCard(appCard)
        .addCard(authorCard)
        .addCard(communityCard)
        .addCard(otherCard)
        .addCard(moreCard)
        .build()
}