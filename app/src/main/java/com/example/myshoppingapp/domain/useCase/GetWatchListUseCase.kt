package com.example.myshoppingapp.domain.useCase

import com.example.myshoppingapp.domain.repo.repo
import javax.inject.Inject

class GetWatchListUseCase @Inject constructor(private val repo: repo) {

    fun getWatchListUseCase() = repo.getWatchList()

}