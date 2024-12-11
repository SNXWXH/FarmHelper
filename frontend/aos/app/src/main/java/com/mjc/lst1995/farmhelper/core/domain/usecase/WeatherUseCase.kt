package com.mjc.lst1995.farmhelper.core.domain.usecase

import com.mjc.lst1995.farmhelper.core.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherUseCase
    @Inject
    constructor(
        private val weatherRepository: WeatherRepository,
    ) {
        suspend fun getTodayWeather(ipAddress: String) = weatherRepository.getTodayWeather(ipAddress)
    }
