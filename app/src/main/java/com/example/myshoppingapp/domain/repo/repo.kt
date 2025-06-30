package com.example.myshoppingapp.domain.repo

import com.example.myshoppingapp.common.State
import com.example.myshoppingapp.domain.models.Category
import com.example.myshoppingapp.domain.models.Product
import com.example.myshoppingapp.domain.models.userData
import kotlinx.coroutines.flow.Flow

interface repo {

    fun getAllCategory(): Flow<State<List<Category>>>

    fun getAllProducts(): Flow<State<List<Product>>>

    fun userRegisterWithEmailAndPassword(userData: userData): Flow<State<String>>

    fun userLoginWithEmailAndPassword(userEmail: String , userPassword: String): Flow<State<String>>

    fun getUserData() : Flow<State<List<userData>>>

    fun  getProductById(productId: String): Flow<State<Product>>
}