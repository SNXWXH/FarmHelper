package com.mjc.lst1995.farmhelper.core.domain.model.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    @SerialName("error")
    val error: String = "",
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
)
