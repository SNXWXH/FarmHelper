package com.mjc.lst1995.farmhelper.core.domain.usecase

import android.content.Context
import android.util.Log
import com.mjc.lst1995.farmhelper.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
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
                        val responseBody = response.body?.string()
                        responseBody?.let {
                            try {
                                val jsonObject = JSONObject(it)
                                val ip = jsonObject.optString("ip", BuildConfig.BASE_IP)
                                Log.d("tttt", ip)
                                ip
                            } catch (e: Exception) {
                                Log.e("tttt", "JSON Parsing error: ${e.message}")
                                BuildConfig.BASE_IP
                            }
                        } ?: BuildConfig.BASE_IP
                    } else {
                        BuildConfig.BASE_IP
                    }
                } catch (e: IOException) {
                    Log.e("tttt", "Network error: ${e.message}")
                    BuildConfig.BASE_IP
                }
            }
        }
    }
