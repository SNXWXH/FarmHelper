package com.mjc.lst1995.farmhelper.core.domain.repository

import com.mjc.lst1995.farmhelper.core.domain.model.weather.Weather

interface WeatherRepository {
    suspend fun getTodayWeather(ipAddress: String): Weather
}
