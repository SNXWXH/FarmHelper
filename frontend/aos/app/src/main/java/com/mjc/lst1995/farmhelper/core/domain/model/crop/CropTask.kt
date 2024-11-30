package com.mjc.lst1995.farmhelper.core.domain.model.crop

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CropTask(
    @SerialName("workName")
    val workName: String,
    @SerialName("workContent")
    val workContent: String,
)
