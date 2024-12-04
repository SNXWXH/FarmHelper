package com.mjc.lst1995.farmhelper.core.domain.model.task

data class RecommendTask(
    val id: Long,
    val content: String,
    val isChecked: Boolean = true,
)
