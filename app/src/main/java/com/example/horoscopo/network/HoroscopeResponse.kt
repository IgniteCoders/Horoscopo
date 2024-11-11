package com.example.horoscopo.network

import com.google.gson.annotations.SerializedName

data class HoroscopeResponse (
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: HoroscopeData
) { }

data class HoroscopeData (
    @SerializedName("date") val date: String,
    @SerializedName("horoscope_data") val luck: String
) { }