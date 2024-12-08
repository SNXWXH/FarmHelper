package com.mjc.lst1995.farmhelper.core.domain.model.task

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SEPARATOR = "q!gL9A"

@Serializable
@Parcelize
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
) : Parcelable

fun Task.toSplit(): Task =
    this.copy(
        workContent = this.workContent.split(SEPARATOR).joinToString("\n\n"),
    )
