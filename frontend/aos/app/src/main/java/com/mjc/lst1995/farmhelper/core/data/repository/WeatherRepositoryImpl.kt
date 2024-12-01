package com.mjc.lst1995.farmhelper.core.data.repository

import com.mjc.lst1995.farmhelper.core.data.network.api.WeatherApi
import com.mjc.lst1995.farmhelper.core.domain.model.weather.Weather
import com.mjc.lst1995.farmhelper.core.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl
    @Inject
    constructor(
        private val weatherApi: WeatherApi,
    ) : WeatherRepository {
        override suspend fun getTodayWeather(ipAddress: String): Weather {
            TODO("Not yet implemented")
        }
    }
