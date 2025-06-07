package com.example.myshoppingapp.domain.models

data class Category(

    var name: String = "",
    var date: Long = System.currentTimeMillis(),
    var imageUri : String = ""
)