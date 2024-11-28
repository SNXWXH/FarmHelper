package com.mjc.lst1995.farmhelper.core.data.network.api

import com.mjc.lst1995.farmhelper.core.data.network.request.user.AuthToken
import com.mjc.lst1995.farmhelper.core.data.network.request.user.NickNameToken
import com.mjc.lst1995.farmhelper.core.data.network.response.user.NickNameResponse
import com.mjc.lst1995.farmhelper.core.data.network.response.user.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserSettingApi {
    @POST("login")
    suspend fun userIdCheck(@Body authToken: AuthToken): SignInResponse

    @POST("login/nickname")
    suspend fun setUserNickName(@Body nickNameToken: NickNameToken): NickNameResponse
}
