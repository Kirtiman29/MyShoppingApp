package com.example.myshoppingapp.domain.useCase

import com.example.myshoppingapp.domain.repo.repo
import javax.inject.Inject

class UserLoginWithEmailAndPasswordUseCase @Inject constructor(private val repo: repo) {

    fun userLoginWithEmailAndPasswordUseCase(userEmail: String, userPassword: String) =
        repo.userLoginWithEmailAndPassword(userEmail = userEmail, userPassword = userPassword)
}