package com.mjc.lst1995.farmhelper.core.domain.repository

import com.mjc.lst1995.farmhelper.core.domain.model.crop.BestCrop
import com.mjc.lst1995.farmhelper.core.domain.model.crop.Crop
import com.mjc.lst1995.farmhelper.core.domain.model.crop.RecommendCrop
import kotlinx.coroutines.flow.Flow

interface CropRepository {
    fun getTodayRecommendedCrops(): Flow<List<RecommendCrop>>

    suspend fun getCropDetail(cropName: String): Flow<List<Crop>>

    suspend fun getMonthOfBestCrop(): Flow<List<BestCrop>>
}
