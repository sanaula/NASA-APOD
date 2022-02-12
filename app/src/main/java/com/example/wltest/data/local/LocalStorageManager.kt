package com.example.wltest.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.wltest.Constant.PREF_NAME

object LocalStorageManager {

    private lateinit var prefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setFirstTime(prefName: String, boolean: Boolean) {
        prefsEditor = prefs.edit()
        with(prefsEditor) {
            putBoolean(prefName, boolean)
            commit()
        }
    }

    fun isFirstTime(prefName: String): Boolean {
        return prefs.getBoolean(prefName, true)
    }

    fun storeTime(prefName: String, prefValue: Long) {
        prefsEditor = prefs.edit()
        with(prefsEditor) {
            putLong(prefName, prefValue)
            commit()
        }
    }

    fun getStoredTime(prefName: String): Long {
        return prefs.getLong(prefName, 0L)
    }
}