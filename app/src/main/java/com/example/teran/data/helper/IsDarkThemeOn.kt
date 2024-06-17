package com.example.teran.data.helper

import android.app.Activity
import android.content.res.Configuration

object IsDarkThemeOn {
    fun isDarkThemeOn(activity: Activity): Boolean =
        activity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK ==
                Configuration.UI_MODE_NIGHT_YES
}