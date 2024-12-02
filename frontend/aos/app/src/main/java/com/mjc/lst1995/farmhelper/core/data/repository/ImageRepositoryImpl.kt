package com.mjc.lst1995.farmhelper.core.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.mjc.lst1995.farmhelper.core.domain.repository.ImageRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ImageRepositoryImpl
    @Inject
    constructor(
        private val storage: FirebaseStorage,
        private val auth: FirebaseAuth,
    ) : ImageRepository {
        override suspend fun uploadImage(uri: Uri): String {
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
            val imagePath = "$userId/${System.currentTimeMillis()}.jpg"
            val storageRef = storage.reference.child(imagePath)

            return try {
                storageRef.putFile(uri).await()
                storageRef.downloadUrl.await().toString()
            } catch (e: Exception) {
                Log.e("ImageRepository", "Failed to upload image", e)
                throw e
            }
        }
    }
