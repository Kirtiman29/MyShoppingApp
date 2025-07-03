package com.example.myshoppingapp.domain.models

import android.net.Uri

data class userData (
    var firstName: String ="",
    var lastName: String ="",
    var email: String ="",
    val password: String ="",
    val confirmPassword: String ="",
    val phoneNumber: String ="",
    val address: String ="",
    val userImage: String =""
)
