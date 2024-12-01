package com.mjc.lst1995.farmhelper.core.domain.repository

import com.mjc.lst1995.farmhelper.core.domain.model.crop.BestCrop
import com.mjc.lst1995.farmhelper.core.domain.model.crop.Crop

interface CropRepository {
    suspend fun getTodayRecommendedCrops(): List<Crop>

    suspend fun getCropDetail(cropName: String): List<Crop>

    suspend fun getMonthOfBestCrop(): List<BestCrop>
}
