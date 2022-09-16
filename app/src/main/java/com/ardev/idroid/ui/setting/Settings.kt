package com.ardev.idroid.ui.setting

import com.ardev.idroid.R
import com.ardev.idroid.app.theme.DarkTheme

object Settings {
    
    val DARK_THEME: SettingLiveData<DarkTheme> = EnumSettingLiveData(R.string.pref_key_dark_theme, R.string.pref_default_value_dark_theme, DarkTheme::class.java)
}
