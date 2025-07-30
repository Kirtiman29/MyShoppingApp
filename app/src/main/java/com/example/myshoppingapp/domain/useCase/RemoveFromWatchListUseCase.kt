package com.example.myshoppingapp.domain.useCase

import com.example.myshoppingapp.domain.repo.repo
import javax.inject.Inject

class RemoveFromWatchListUseCase @Inject constructor(private val repo: repo) {

    fun removeFromWatchListUseCase(productId: String) = repo.removeFromWatchList(productId)

}