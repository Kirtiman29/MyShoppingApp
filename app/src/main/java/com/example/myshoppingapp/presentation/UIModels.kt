package com.example.myshoppingapp.presentation

import com.example.myshoppingapp.data.repoimple.repoimple
import com.example.myshoppingapp.domain.repo.repo
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)

object UIModels {
    @Provides

    fun provideRepo(
        firebaseFirestore: FirebaseFirestore
    ): repo {
        return repoimple(firebaseFirestore)

    }
}