package com.example.myshoppingapp.domain.models



data class WatchlistItem(
    val productId: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val imageUrl: String = ""
)
