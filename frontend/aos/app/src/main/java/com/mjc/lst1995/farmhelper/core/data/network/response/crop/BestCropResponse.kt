package com.mjc.lst1995.farmhelper.core.data.network.response.crop

import com.mjc.lst1995.farmhelper.core.domain.model.crop.BestCrop
import kotlinx.serialization.Serializable

@Serializable
data class BestCropResponse(
    val bestCrops: List<BestCrop>
)
