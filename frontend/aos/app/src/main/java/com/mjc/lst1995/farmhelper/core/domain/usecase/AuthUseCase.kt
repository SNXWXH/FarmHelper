package com.mjc.lst1995.farmhelper.core.domain.usecase

import com.mjc.lst1995.farmhelper.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    )
