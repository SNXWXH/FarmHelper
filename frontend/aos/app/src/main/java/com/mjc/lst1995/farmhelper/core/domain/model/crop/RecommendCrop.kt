package com.mjc.lst1995.farmhelper.core.domain.model.crop

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class RecommendCrop(
    @SerialName("cropName")
    val cropName: String,
    @SerialName("imageUrl")
    val imageUrl: String?,
    @SerialName("description")
    val description: String
)

fun RecommendCrop.toDescription(): Description {
    val json = Json { ignoreUnknownKeys = true }
    return json.decodeFromString(this.description)
}
