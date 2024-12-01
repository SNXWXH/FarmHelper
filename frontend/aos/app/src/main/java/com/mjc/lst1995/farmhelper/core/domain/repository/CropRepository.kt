package com.mjc.lst1995.farmhelper.core.domain.repository

import com.mjc.lst1995.farmhelper.core.domain.model.crop.BestCrop
import com.mjc.lst1995.farmhelper.core.domain.model.crop.Crop
import kotlinx.coroutines.flow.Flow

interface CropRepository {
    suspend fun getTodayRecommendedCrops(): Flow<List<Crop>>

    suspend fun getCropDetail(cropName: String): Flow<List<Crop>>

    suspend fun getMonthOfBestCrop(): Flow<List<BestCrop>>
}
