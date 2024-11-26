package com.amk.photogenerator.util

import android.content.Context
import android.content.SharedPreferences

object FirstRunPreferences {

    private const val PREFS_NAME = "app_preferences"
    private const val KEY_FIRST_RUN = "is_first_run"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setFirstRunCompleted(context: Context) {
        getPreferences(context).edit().putBoolean(KEY_FIRST_RUN, false).apply()
    }

    fun isFirstRun(context: Context): Boolean {
        return getPreferences(context).getBoolean(KEY_FIRST_RUN, true)
    }
}