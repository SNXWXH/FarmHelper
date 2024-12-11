package com.mjc.lst1995.farmhelper.core.domain.model.crop

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crop(
    @SerialName("cropId")
    val id: Long,
    @SerialName("cropName")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("imageUrl")
    val imageUrl: String?
)
