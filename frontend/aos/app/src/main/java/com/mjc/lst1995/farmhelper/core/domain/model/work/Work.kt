package com.mjc.lst1995.farmhelper.core.domain.model.work

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Work(
    @SerialName("cropId")
    val cropId: Long,
    @SerialName("cropName")
    val cropName: String,
    @SerialName("imageUrl")
    val imageUrl: String?,
    @SerialName("cropDate")
    val cropDate: String,
) : Parcelable
