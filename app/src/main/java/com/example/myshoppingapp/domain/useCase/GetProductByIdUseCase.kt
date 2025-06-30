package com.example.myshoppingapp.domain.useCase

import com.example.myshoppingapp.domain.repo.repo
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val repo: repo) {


    fun getProductByIdUseCase(productId: String) = repo.getProductById(productId)
}