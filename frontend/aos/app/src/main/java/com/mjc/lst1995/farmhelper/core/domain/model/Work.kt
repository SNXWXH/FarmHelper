package com.mjc.lst1995.farmhelper.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Work(
    val cropName: String,
    val imageUrl: String,
    val cropDate: String
)
