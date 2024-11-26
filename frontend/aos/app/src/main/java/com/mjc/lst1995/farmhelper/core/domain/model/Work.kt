package com.mjc.lst1995.farmhelper.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Work(
    val cropName: String,
    val imageUrl: String,
    val cropDate: String
): Parcelable
