package com.mjc.lst1995.farmhelper.core.data.network.api

import com.mjc.lst1995.farmhelper.core.data.network.response.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {
    @GET("main/weather/{ipAddress}")
    suspend fun getTodayWeather(
        @Path("ipAddress") ipAddress: String,
    ): WeatherResponse
}
