package com.mjc.lst1995.farmhelper.core.data.network.response.crop

import com.mjc.lst1995.farmhelper.core.domain.model.crop.Crop
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedCropsResponse(
    val crops: List<Crop>
)
