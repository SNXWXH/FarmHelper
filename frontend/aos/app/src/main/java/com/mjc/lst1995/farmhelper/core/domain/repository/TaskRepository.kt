package com.mjc.lst1995.farmhelper.core.domain.repository

interface TaskRepository {
    fun getRecommendTasks(cropId: Long): List<String>
}
