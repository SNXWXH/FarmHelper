package com.mjc.lst1995.farmhelper.feature.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.domain.model.crop.Crop
import com.mjc.lst1995.farmhelper.core.domain.model.crop.RecommendCrop
import com.mjc.lst1995.farmhelper.core.domain.usecase.AuthUseCase
import com.mjc.lst1995.farmhelper.core.domain.usecase.CropUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val cropUseCase: CropUseCase,
) : ViewModel() {
    val recommendedCrops: Flow<List<RecommendCrop>> = cropUseCase.getTodayRecommendedCrops()

    private val loginState: Flow<Boolean> = authUseCase.loginStateFlow()

    private val _isLoginMenusVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val isLoginMenusVisibility: LiveData<Int> = _isLoginMenusVisibility

    private val _isNotLoginMenusVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val isNotLoginMenusVisibility: LiveData<Int> = _isNotLoginMenusVisibility

    private val _navigationItem: MutableLiveData<Int> = MutableLiveData(R.menu.menu_not_login)
    val navigationItem: LiveData<Int> = _navigationItem

    init {
        setObserverViewVisibility()
    }

    fun firebaseSignOut() {
        authUseCase.firebaseSignOut()
    }

    private fun setObserverViewVisibility() {
        viewModelScope.launch {
            loginState.collect { isLogin ->
                when (isLogin) {
                    true -> {
                        _isLoginMenusVisibility.postValue(View.VISIBLE)
                        _isNotLoginMenusVisibility.postValue(View.GONE)
                        _navigationItem.postValue(R.menu.menu_login)
                    }

                    false -> {
                        _isLoginMenusVisibility.postValue(View.GONE)
                        _isNotLoginMenusVisibility.postValue(View.VISIBLE)
                        _navigationItem.postValue(R.menu.menu_not_login)
                    }
                }
            }
        }
    }


}
