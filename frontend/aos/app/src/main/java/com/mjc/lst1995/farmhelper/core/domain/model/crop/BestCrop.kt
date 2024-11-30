package com.mjc.lst1995.farmhelper.core.domain.model.crop

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BestCrop(
    @SerialName("cropName")
    val cropName: String,
    @SerialName("count")
    val count: Int
)
