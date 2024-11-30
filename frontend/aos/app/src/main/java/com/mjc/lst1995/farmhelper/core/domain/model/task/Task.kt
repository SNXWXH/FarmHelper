package com.mjc.lst1995.farmhelper.core.domain.model.task

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    @SerialName("workId")
    val workId: Long,
    @SerialName("workWeather")
    val workWeather: String,
    @SerialName("workDate")
    val workDate: String,
    @SerialName("workContent")
    val workContent: String,
    @SerialName("workTemperature")
    val workTemperature: String,
)
