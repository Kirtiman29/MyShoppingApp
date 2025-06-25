package com.example.myshoppingapp.domain.useCase

import com.example.myshoppingapp.domain.models.userData
import com.example.myshoppingapp.domain.repo.repo
import javax.inject.Inject

class UserRegisterWithEmailAndPasswordUseCase @Inject constructor(private val repo: repo) {

    fun userRegisterWithEmailAndPasswordUseCase(userData: userData) = repo.userRegisterWithEmailAndPassword(userData)
}