package com.mjc.lst1995.farmhelper.core.domain.model.task

import com.mjc.lst1995.farmhelper.core.domain.model.weather.Weather

data class OtherDetail(
    val today: String,
    val cropName: String,
    val nickname: String,
    val weather: Weather,
)
