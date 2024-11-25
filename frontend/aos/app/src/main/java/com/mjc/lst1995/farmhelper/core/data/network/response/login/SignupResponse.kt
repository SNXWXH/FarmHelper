package com.mjc.lst1995.farmhelper.core.data.network.response.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignupResponse(
    @SerialName("isJoined")
    val isJoined: Boolean,
)