package com.mjc.lst1995.farmhelper.core.data.network.response.work

import com.mjc.lst1995.farmhelper.core.domain.model.work.Work
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorksResponse(
    @SerialName("workList")
    val workList: List<Work>,
    @SerialName("nickname")
    val nickName: String
)
