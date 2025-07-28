package com.example.myshoppingapp.domain.models

data class CartItem(

    var productId: String = "",
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var quantity: Int = 1,
    var imageUrl: String = ""

)