package com.example.myshoppingapp.domain.useCase

import com.example.myshoppingapp.domain.models.CartItem
import com.example.myshoppingapp.domain.repo.repo
import javax.inject.Inject

class GetCartItemDataUseCase @Inject constructor(private val repo: repo) {

    fun getCartItemDataUseCase(cartItem: CartItem) = repo.getCartItem(cartItem)
}