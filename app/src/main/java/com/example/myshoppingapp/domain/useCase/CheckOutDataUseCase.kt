package com.example.myshoppingapp.domain.useCase

import com.example.myshoppingapp.domain.models.CheckOutDataModels
import com.example.myshoppingapp.domain.repo.repo
import javax.inject.Inject

class CheckOutDataUseCase @Inject constructor(private val repo: repo) {

    fun checkOutDataUseCase(checkData: CheckOutDataModels) = repo.checkOutData(checkOutData = checkData)
}