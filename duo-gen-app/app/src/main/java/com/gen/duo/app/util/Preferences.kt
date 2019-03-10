package com.gen.duo.app.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by israjhaliri on 3/8/18.
 */
data class Preferences(var context: Context) {

    var preferences = PreferenceManager.getDefaultSharedPreferences(context);

    fun setToken(token: String) {
        preferences.edit().putString("token", token).commit();
    }

    fun getToken(): String {
        val token: String = preferences.getString("token", "")
        return token
    }
}