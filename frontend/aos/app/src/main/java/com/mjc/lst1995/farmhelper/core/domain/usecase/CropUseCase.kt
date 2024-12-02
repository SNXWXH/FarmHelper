package com.mjc.lst1995.farmhelper.core.domain.usecase

import com.mjc.lst1995.farmhelper.core.domain.repository.CropRepository
import javax.inject.Inject

class CropUseCase
    @Inject
    constructor(
        private val cropRepository: CropRepository,
    ) {
        fun getTodayRecommendedCrops() = cropRepository.getTodayRecommendedCrops()
    }
