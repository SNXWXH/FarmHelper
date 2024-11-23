package com.mjc.lst1995.farmhelper.core.data.networl.api

import com.mjc.lst1995.farmhelper.core.data.networl.request.AuthToken
import com.mjc.lst1995.farmhelper.core.data.networl.response.login.SignupResponse
import retrofit2.http.POST

interface UserSettingApi {
    @POST("login")
    suspend fun userIdCheck(authToken: AuthToken): SignupResponse
}
