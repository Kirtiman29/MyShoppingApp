package com.example.myshoppingapp.domain.useCase

import com.example.myshoppingapp.domain.repo.repo
import javax.inject.Inject

class GetAllProductUseCase @Inject constructor(private val repo: repo) {

    fun getAllProductUseCase() = repo.getAllProducts()

}