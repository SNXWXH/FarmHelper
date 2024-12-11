package com.mjc.lst1995.farmhelper.core.domain.repository

import android.net.Uri

interface ImageRepository {
    suspend fun uploadImage(uri: Uri): String
}
