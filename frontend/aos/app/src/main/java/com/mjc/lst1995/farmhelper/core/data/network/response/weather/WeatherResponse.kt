package com.mjc.lst1995.farmhelper.core.data.network.response.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("date")
    val date: String,
    @SerialName("description")
    val description: String,
    @SerialName("feels_like")
    val feels_like: String,
    @SerialName("humidity")
    val humidity: String,
    @SerialName("region")
    val region: String,
    @SerialName("sunrise")
    val sunrise: String,
    @SerialName("temperature")
    val temperature: String,
    @SerialName("wind_speed")
    val wind_speed: String,
)
