package com.mjc.lst1995.farmhelper.feature.work

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjc.lst1995.farmhelper.core.domain.usecase.ImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class WorkCreateViewModel
    @Inject
    constructor(
        private val imageUseCase: ImageUseCase,
    ) : ViewModel() {
        private val imageFile = MutableLiveData<Uri?>()

        fun setImageFile(uri: Uri?) {
            imageFile.value = uri
        }

        fun createWork() {
            viewModelScope.launch {
                val imageUrl = uploadImage(imageFile.value)
            }
        }

        private suspend fun uploadImage(uri: Uri?): String? {
            return uri?.let {
                imageUseCase.uploadImage(uri)
            } ?: return null
        }
    }
