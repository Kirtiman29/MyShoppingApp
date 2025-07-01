package com.example.myshoppingapp.domain.useCase

import android.net.Uri
import com.example.myshoppingapp.domain.repo.repo
import javax.inject.Inject

class UploadUserImageUseCase @Inject constructor(private val repo: repo)  {

    fun uploadUserImageUseCase(imageUri: Uri) = repo.uploadUserImage(imageUri)
}