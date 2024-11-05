package com.example.horoscopo.utils

import android.content.Context

class SessionManager(val context: Context) {

    private val prefs = context.getSharedPreferences("horoscope_session", Context.MODE_PRIVATE)

    fun setFavorite(horoscopeId: String) {
        val editor = prefs.edit()
        editor.putString("favorite_horoscope", horoscopeId)
        editor.apply()
    }

    fun getFavorite(): String {
        return prefs.getString("favorite_horoscope", "")!!
    }

    fun isFavorite(horoscopeId: String) : Boolean {
        return horoscopeId == getFavorite()
    }


}