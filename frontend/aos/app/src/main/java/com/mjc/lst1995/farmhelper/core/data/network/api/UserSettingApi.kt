package com.mjc.lst1995.farmhelper.core.data.network.api

import com.mjc.lst1995.farmhelper.core.data.network.request.AuthToken
import com.mjc.lst1995.farmhelper.core.data.network.response.login.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserSettingApi {
    @POST("login")
    suspend fun userIdCheck(@Body authToken: AuthToken): SignupResponse
}
