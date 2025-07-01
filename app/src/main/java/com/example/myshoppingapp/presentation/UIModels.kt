package com.example.myshoppingapp.presentation

import com.example.myshoppingapp.data.repoimple.repoimple
import com.example.myshoppingapp.domain.repo.repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)

object UIModels {
    @Provides

    fun provideRepo(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        firebaseStorage: FirebaseStorage
    ): repo {
        return repoimple(firebaseFirestore,firebaseAuth,firebaseStorage)

    }
}