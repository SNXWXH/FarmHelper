package com.mjc.lst1995.farmhelper.core.data.network.api

import com.mjc.lst1995.farmhelper.core.data.network.response.crop.BestCropResponse
import com.mjc.lst1995.farmhelper.core.data.network.response.crop.RecommendedCropsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CropApi {
    @GET("main/todaycrop")
    suspend fun getTodayRecommendedCrops(): RecommendedCropsResponse

    @GET("main/todaycrop/detail/{cropName}")
    suspend fun getCropDetail(
        @Path("cropName") cropName: String,
    ): RecommendedCropsResponse

    @GET("main/bestcrop")
    suspend fun getMonthOfBestCrop(): BestCropResponse
}
