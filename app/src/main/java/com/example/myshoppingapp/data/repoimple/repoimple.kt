package com.example.myshoppingapp.data.repoimple

import com.example.myshoppingapp.common.CATEGORY
import com.example.myshoppingapp.common.Products
import com.example.myshoppingapp.common.State
import com.example.myshoppingapp.domain.models.Category
import com.example.myshoppingapp.domain.models.Product
import com.example.myshoppingapp.domain.models.userData
import com.example.myshoppingapp.domain.repo.repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class repoimple
@Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : repo {
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
                val productData = it.documents.mapNotNull {
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

    override fun userRegisterWithEmailAndPassword(userData: userData): Flow<State<String>> = callbackFlow {

        trySend(State.Loading)

        firebaseAuth.createUserWithEmailAndPassword(userData.email,userData.password)
            .addOnSuccessListener {
                firebaseFirestore.collection("USERS").document(it.user?.uid.toString())
                    .set(userData)
                    .addOnSuccessListener {
                        trySend(State.Success("User Register Successfully"))
                    }
                    .addOnFailureListener {
                        trySend(State.Error(it.toString()))

                    }
            }
            .addOnFailureListener {
                trySend(State.Error(it.toString()))
            }
        awaitClose {
            close()
        }

    }

}