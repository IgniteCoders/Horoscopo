package com.example.horoscopo.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HoroscopeService {
    @GET("{period}")
    suspend fun getHoroscopeData(@Path("period") period: String, @Query("sign") id: String) : HoroscopeResponse
}