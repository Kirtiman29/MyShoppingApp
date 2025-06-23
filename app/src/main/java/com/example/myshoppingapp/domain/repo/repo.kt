package com.example.myshoppingapp.domain.repo

import com.example.myshoppingapp.common.State
import com.example.myshoppingapp.domain.models.Category
import com.example.myshoppingapp.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface repo {

    fun getAllCategory(): Flow<State<List<Category>>>

    fun getAllProducts(): Flow<State<List<Product>>>
}