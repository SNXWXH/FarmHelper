package com.mjc.lst1995.farmhelper.core.data.networl.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    @SerialName("userId")
    val uuid: String
)
