package com.mjc.lst1995.farmhelper.core.domain.model.crop

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendCrop(
    @SerialName("cropName")
    val cropName: String,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("description")
    val description: Description
)
