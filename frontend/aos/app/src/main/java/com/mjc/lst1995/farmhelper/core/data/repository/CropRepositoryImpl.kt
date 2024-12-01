package com.mjc.lst1995.farmhelper.core.data.repository

import com.mjc.lst1995.farmhelper.core.data.network.api.CropApi
import com.mjc.lst1995.farmhelper.core.domain.model.crop.BestCrop
import com.mjc.lst1995.farmhelper.core.domain.model.crop.Crop
import com.mjc.lst1995.farmhelper.core.domain.repository.CropRepository
import javax.inject.Inject

class CropRepositoryImpl
    @Inject
    constructor(
        private val cropApi: CropApi,
    ) : CropRepository {
    override suspend fun getTodayRecommendedCrops(): List<Crop> {
        TODO("Not yet implemented")
    }

    override suspend fun getCropDetail(cropName: String): List<Crop> {
        TODO("Not yet implemented")
    }

    override suspend fun getMonthOfBestCrop(): List<BestCrop> {
        TODO("Not yet implemented")
    }
}
