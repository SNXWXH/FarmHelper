package com.mjc.lst1995.farmhelper.core.data.network.response.weather

import com.mjc.lst1995.farmhelper.core.domain.model.weather.Weather

data class WeatherResponse(
    val date: String,
    val description: String,
    val feels_like: String,
    val humidity: String,
    val region: String,
    val sunrise: String,
    val temperature: String,
    val wind_speed: String,
)

fun WeatherResponse.toWeather() =
    Weather(
        this.date,
        this.description,
        this.feels_like,
        this.humidity,
        this.region,
        this.sunrise,
        this.temperature,
        this.wind_speed,
    )
