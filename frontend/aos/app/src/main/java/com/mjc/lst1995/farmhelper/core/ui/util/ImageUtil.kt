package com.mjc.lst1995.farmhelper.core.ui.util

import androidx.annotation.DrawableRes
import com.mjc.lst1995.farmhelper.R

object ImageUtil {
    @DrawableRes
    fun getImageResource(imageName: String) =
        when (imageName) {
            "고구마" -> R.drawable.sweet_potato
            "감자" -> R.drawable.potato
            "밀" -> R.drawable.wheat
            "쌀" -> R.drawable.rice
            "옥수수" -> R.drawable.corn
            "보리" -> R.drawable.barley
            "양배추" -> R.drawable.cabbage
            "토마토" -> R.drawable.tomato
            "브로콜리" -> R.drawable.broccoli
            "피망" -> R.drawable.bell_pepper
            "호박" -> R.drawable.pumpkin
            "오이" -> R.drawable.cucumber
            "당근" -> R.drawable.carrot
            "시금치" -> R.drawable.spinach
            "마늘" -> R.drawable.garlic
            "사과" -> R.drawable.apple
            "포도" -> R.drawable.grape
            "딸기" -> R.drawable.strawberry
            "녹두" -> R.drawable.mung_bean
            "수박" -> R.drawable.watermelon
            "샤인머스캣" -> R.drawable.shine_muscat
            "카카오" -> R.drawable.cacao
            else -> R.mipmap.ic_launcher
        }
}
