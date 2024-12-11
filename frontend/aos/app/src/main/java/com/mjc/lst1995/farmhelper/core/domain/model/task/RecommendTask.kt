package com.mjc.lst1995.farmhelper.core.domain.model.task

data class RecommendTask(
    val id: Int,
    val content: String,
    val isChecked: Boolean = true,
)
