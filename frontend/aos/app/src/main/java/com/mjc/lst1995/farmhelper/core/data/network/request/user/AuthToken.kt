package com.mjc.lst1995.farmhelper.core.data.network.request.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    @SerialName("userId")
    val userId: String
)
