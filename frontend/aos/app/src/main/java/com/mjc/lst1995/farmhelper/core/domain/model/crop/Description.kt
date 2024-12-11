package com.mjc.lst1995.farmhelper.core.domain.model.crop

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Description(
    @SerialName("Planting season")
    val plantingSeason: String,
    @SerialName("Harvest season")
    val harvestSeason: String,
    @SerialName("Description")
    val content: String
)
