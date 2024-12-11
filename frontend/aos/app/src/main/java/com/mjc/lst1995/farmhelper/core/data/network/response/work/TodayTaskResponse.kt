package com.mjc.lst1995.farmhelper.core.data.network.response.work

import com.mjc.lst1995.farmhelper.core.domain.model.crop.CropTask
import kotlinx.serialization.Serializable

@Serializable
data class TodayTaskResponse(
    val tasks: List<CropTask>
)
