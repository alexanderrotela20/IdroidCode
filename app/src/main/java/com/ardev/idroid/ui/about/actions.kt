package com.ardev.idroid.ui.about

import com.ardev.idroid.app.IdroidApplication.context

fun createAppTitleItem(desc: String): MaterialAboutTitleItem {
    val packageManager = context.packageManager
    val applicationInfo = context.applicationInfo
    val appName = packageManager.getApplicationLabel(applicationInfo)?.toString() ?: ""
    val appIcon = packageManager.getApplicationIcon(applicationInfo)
    return MaterialAboutTitleItem(appName, desc, appIcon)
}

fun createVersionActionItem(icon: Drawable, text: CharSequence): MaterialAboutActionItem {
    var versionName = ""
    var versionCode = 0
    try {
        val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        versionName = pInfo.versionName
        versionCode = pInfo.versionCode
    } catch (ignored: PackageManager.NameNotFoundException) { }      
    val subText = "$versionName ($versionCode)")
    return MaterialAboutActionItem.Builder()
        .text(text)
        .subText(subText)
        .icon(icon)
        .build()
}

fun createWebsiteActionItem(icon: Drawable, text: CharSequence, url: String): MaterialAboutActionItem {
    return MaterialAboutActionItem.Builder()
        .text(text)
        .icon(icon)
        .setOnClickAction(createWebsiteOnClickAction(url))
        .build()
}

fun createEmailItem(icon: Drawable, text: CharSequence, email: String, emailSubject: String = "", chooserTitle: CharSequence = ""): MaterialAboutActionItem {
    return MaterialAboutActionItem.Builder()
        .text(text)
        .icon(icon)
        .setOnClickAction(createEmailOnClickAction(email, emailSubject, chooserTitle))
        .build()
}

private fun createWebsiteOnClickAction(url: String): MaterialAboutItemOnClickAction {
    return object : MaterialAboutItemOnClickAction {
        override fun onClick() {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            try {
                context.startActivity(intent)
            } catch (e: Exception) {             
                Toast.makeText(context, R.string.mal_activity_exception, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

private fun createEmailOnClickAction(email: String, emailSubject: String, chooserTitle: CharSequence): MaterialAboutItemOnClickAction {
    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)

    return object : MaterialAboutItemOnClickAction {
        override fun onClick() {
            try {
                context.startActivity(Intent.createChooser(emailIntent, chooserTitle))
            } catch (e: Exception) {
                Toast.makeText(context, R.string.mal_activity_exception, Toast.LENGTH_SHORT).show()
            }
        }
    }
}


