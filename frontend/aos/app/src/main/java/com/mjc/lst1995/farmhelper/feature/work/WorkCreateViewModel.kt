package com.mjc.lst1995.farmhelper.feature.work

import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjc.lst1995.farmhelper.core.domain.usecase.ImageUseCase
import com.mjc.lst1995.farmhelper.core.domain.usecase.WorkError
import com.mjc.lst1995.farmhelper.core.domain.usecase.WorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkCreateViewModel
    @Inject
    constructor(
        private val imageUseCase: ImageUseCase,
        private val workUseCase: WorkUseCase,
    ) : ViewModel() {
        private val imageFile = MutableLiveData<Uri?>()
        val selectedDate: MutableLiveData<Long> = MutableLiveData()
        val cropName: MutableLiveData<String> = MutableLiveData()
        val resultMessage: MutableLiveData<String> = MutableLiveData()
        val progress: MutableLiveData<Int> = MutableLiveData(null)

        init {
            selectedDate.value = System.currentTimeMillis()
        }

        fun setImageFile(uri: Uri?) {
            imageFile.value = uri
        }

        fun createWork() {
            viewModelScope.launch {
                progress.postValue(View.VISIBLE)
                val imageUrl = uploadImage(imageFile.value)
                val cropName = cropName.value
                val date = selectedDate.value
                val valid = workUseCase.isValid(cropName, date)
                if (valid != WorkError.NONE.message) {
                    resultMessage.value = valid
                    progress.postValue(View.GONE)
                    return@launch
                }
                workUseCase.createWork(imageUrl, cropName!!, date!!)
                resultMessage.value = "작업이 생성되었습니다."
                progress.postValue(View.GONE)
            }
        }

        private suspend fun uploadImage(uri: Uri?): String? {
            return uri?.let {
                imageUseCase.uploadImage(uri)
            } ?: return null
        }
    }
