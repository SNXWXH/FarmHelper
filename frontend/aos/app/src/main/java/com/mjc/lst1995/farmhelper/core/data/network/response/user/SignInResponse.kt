package com.mjc.lst1995.farmhelper.core.data.network.response.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    @SerialName("isJoined")
    val isJoined: Boolean,
)
