package com.mjc.lst1995.farmhelper.core.domain.usecase

import android.net.Uri
import com.mjc.lst1995.farmhelper.core.domain.repository.ImageRepository
import javax.inject.Inject

class ImageUseCase
    @Inject
    constructor(
        private val imageRepository: ImageRepository,
    ) {
        suspend fun uploadImage(uri: Uri): String = imageRepository.uploadImage(uri)
    }
