package com.mjc.lst1995.farmhelper.core.domain.model.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    @SerialName("region")
    val region: String = "",
    @SerialName("date")
    val date: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("humidity")
    val humidity: String = "",
    @SerialName("sunrise")
    val sunrise: String = "",
    @SerialName("temperature")
    val temperature: String = "",
    @SerialName("wind_speed")
    val windSpeed: String = "",
    @SerialName("feels_like")
    val feelsLike: String = "",
)
