package com.mjc.lst1995.farmhelper.core.data.network.response.work

import com.mjc.lst1995.farmhelper.core.domain.model.task.Task
import com.mjc.lst1995.farmhelper.core.domain.model.weather.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkDetailResponse(
    @SerialName("workLog")
    val workLog: List<Task>,
    @SerialName("today")
    val today: String,
    @SerialName("cropName")
    val cropName: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("weather")
    val weather: Weather,
)
