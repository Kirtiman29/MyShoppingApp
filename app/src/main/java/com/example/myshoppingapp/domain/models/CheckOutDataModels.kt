package com.example.myshoppingapp.domain.models

data class CheckOutDataModels (


    val email : String = "",
    val country : String = "",
    val address : String = "",
    val city : String = "",
    val state : String = "",
    val pincode : String = "",
    val firstName : String = "",
    val lastName : String = "",
    val phone : String = "",
    val date: Long = System.currentTimeMillis(),
    val isCOD : Boolean = true,

)