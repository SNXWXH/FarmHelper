package com.mjc.lst1995.farmhelper.core.domain.usecase

import android.content.Context
import com.mjc.lst1995.farmhelper.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

class NetworkUseCase
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val client: OkHttpClient,
    ) {
        suspend fun getPublicIPAddress(): String {
            val request =
                Request
                    .Builder()
                    .url("https://api.ipify.org?format=json")
                    .build()

            return withContext(Dispatchers.IO) {
                try {
                    val response = client.newCall(request).execute()
                    if (response.isSuccessful) {
                        response.body?.string() ?: "No Response Body"
                    } else {
                        BuildConfig.BASE_IP
                    }
                } catch (e: IOException) {
                    BuildConfig.BASE_IP
                }
            }
        }
    }
