package com.example.myshoppingapp.domain.useCase

import com.example.myshoppingapp.domain.models.WatchlistItem
import com.example.myshoppingapp.domain.repo.repo
import com.example.myshoppingapp.presentation.WatchListState
import javax.inject.Inject

class AddToWatchlistUseCase  @Inject constructor(private val repo: repo) {

    fun addToWatchlistUseCase(watchListItems: WatchlistItem) = repo.addToWatchList(watchListItems)
}