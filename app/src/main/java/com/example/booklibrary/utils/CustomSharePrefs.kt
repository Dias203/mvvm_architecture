package com.example.booklibrary.utils

import android.content.Context
import android.content.SharedPreferences

object CustomSharePrefs {
    private const val PREF_NAME = "MyPrefs"
    private const val SCROLL_POS = "scroll_position"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    fun saveScrollPos(context: Context, scrollPos: Int) {
        ECOLog.showLog("Position: $scrollPos")
        getPrefs(context).edit()
            .putInt(SCROLL_POS, scrollPos)
            .apply()
    }
}