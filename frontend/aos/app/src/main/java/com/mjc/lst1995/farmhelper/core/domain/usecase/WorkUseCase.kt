package com.mjc.lst1995.farmhelper.core.domain.usecase

import com.mjc.lst1995.farmhelper.core.domain.model.task.toSplit
import com.mjc.lst1995.farmhelper.core.domain.repository.WorkRepository
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

enum class WorkError(
    val message: String,
) {
    NAME_EMPTY("이름을 입력해주세요."),
    NAME_NOT_KOREAN("한글로 입력해주세요."),
    DATE_EMPTY("날짜를 선택해주세요."),
    NONE(""),
}

class WorkUseCase
    @Inject
    constructor(
        private val workRepository: WorkRepository,
    ) {
        suspend fun createWork(
            imageUrl: String?,
            cropName: String,
            date: Long,
        ): Boolean {
            val dateString = formatLongToDate(date)
            return workRepository.createWork(cropName, dateString, imageUrl)
        }

        suspend fun getWorkTaskDetails(
            cropId: Long,
            ipAddress: String,
        ) = workRepository.getWorkTaskDetails(cropId, ipAddress).map { it.map { it.toSplit() } }

        fun getWorks() = workRepository.getWorks()

        fun isValid(
            cropName: String?,
            date: Long?,
        ): String {
            val errorMessage = nameCheck(cropName)
            return when (nameCheck(cropName)) {
                WorkError.NAME_EMPTY -> errorMessage.message
                WorkError.NAME_NOT_KOREAN -> errorMessage.message
                else -> dateCheck(date).message
            }
        }

        private fun nameCheck(name: String?): WorkError {
            // 이름이 비어 있을 경우
            if (name.isNullOrEmpty()) return WorkError.NAME_EMPTY

            // 한글이 아닌 문자가 포함되어 있는지 확인
            val regex = "^[가-힣]*$".toRegex()
            if (!regex.matches(name)) return WorkError.NAME_NOT_KOREAN

            return WorkError.NONE
        }

        private fun dateCheck(date: Long?): WorkError {
            if (date == null) return WorkError.DATE_EMPTY
            return WorkError.NONE
        }

        private fun formatLongToDate(longDate: Long): String {
            // SimpleDateFormat에 사용할 패턴 설정
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            // Date 객체로 변환
            val date = Date(longDate)
            // 포맷된 문자열 반환
            return sdf.format(date)
        }
    }
