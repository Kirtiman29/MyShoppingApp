package com.example.myshoppingapp.data.repoimple

import com.example.myshoppingapp.common.CATEGORY
import com.example.myshoppingapp.common.Products
import com.example.myshoppingapp.common.State
import com.example.myshoppingapp.domain.models.Category
import com.example.myshoppingapp.domain.models.Product
import com.example.myshoppingapp.domain.repo.repo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class repoimple
@Inject constructor(private val firebaseFirestore: FirebaseFirestore) : repo {
    override fun getAllCategory(): Flow<State<List<Category>>> = callbackFlow {

        trySend(State.Loading)
        firebaseFirestore.collection(CATEGORY).get()
            .addOnSuccessListener {
                val categoryData = it.documents.mapNotNull {
                    it.toObject(Category::class.java)

                }
                trySend(State.Success(categoryData))
            }
            .addOnFailureListener {
                trySend(State.Error(it.toString()))
            }
        awaitClose {
            close()
        }


    }

    override fun getAllProducts(): Flow<State<List<Product>>> = callbackFlow {
        trySend(State.Loading)

        firebaseFirestore.collection(Products).get()
            .addOnSuccessListener {
                val productData = it.documents.mapNotNull{
                    it.toObject(Product::class.java)

                }

                trySend(State.Success(productData))
            }
            .addOnFailureListener {
                trySend(State.Error(it.toString()))
            }
        awaitClose {
            close()

            }

    }


}