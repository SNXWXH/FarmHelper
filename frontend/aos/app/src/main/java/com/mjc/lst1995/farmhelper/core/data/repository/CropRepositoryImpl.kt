package com.mjc.lst1995.farmhelper.core.data.repository

import com.mjc.lst1995.farmhelper.core.data.network.api.CropApi
import com.mjc.lst1995.farmhelper.core.domain.model.crop.BestCrop
import com.mjc.lst1995.farmhelper.core.domain.model.crop.RecommendCrop
import com.mjc.lst1995.farmhelper.core.domain.repository.CropRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CropRepositoryImpl
    @Inject
    constructor(
        private val cropApi: CropApi,
    ) : CropRepository {
        override fun getTodayRecommendedCrops(): Flow<List<RecommendCrop>> =
            flow {
                emit(cropApi.getTodayRecommendedCrops())
            }

        override suspend fun getCropDetail(cropName: String): List<RecommendCrop> = cropApi.getCropDetail(cropName)

        override suspend fun getMonthOfBestCrop(): List<BestCrop> = cropApi.getMonthOfBestCrop()
    }
