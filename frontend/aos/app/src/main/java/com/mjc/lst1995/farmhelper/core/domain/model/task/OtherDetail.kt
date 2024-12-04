package com.mjc.lst1995.farmhelper.core.domain.model.task

import com.mjc.lst1995.farmhelper.core.domain.model.weather.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtherDetail(
    @SerialName("today")
    val today: String,
    val cropName: String,
    val nickname: String,
    val weather: Weather,
)
