package com.example.myshoppingapp.data.repoimple

import android.net.Uri
import com.example.myshoppingapp.common.CATEGORY
import com.example.myshoppingapp.common.CheckOutDetails
import com.example.myshoppingapp.common.Products
import com.example.myshoppingapp.common.State
import com.example.myshoppingapp.common.USERS
import com.example.myshoppingapp.domain.models.Category
import com.example.myshoppingapp.domain.models.CheckOutDataModels
import com.example.myshoppingapp.domain.models.Product
import com.example.myshoppingapp.domain.models.userData
import com.example.myshoppingapp.domain.repo.repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class repoimple
@Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage
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

    override fun userRegisterWithEmailAndPassword(userData: userData): Flow<State<String>> =
        callbackFlow {

            trySend(State.Loading)

            firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password)
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

    override fun userLoginWithEmailAndPassword(
        userEmail: String,
        userPassword: String
    ): Flow<State<String>> = callbackFlow {
        trySend(State.Loading)
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnSuccessListener {
                trySend(State.Success("User Login Successfully"))
            }
            .addOnFailureListener {
                trySend(State.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }

    }

    override fun getUserData(): Flow<State<List<userData>>> = callbackFlow {
        trySend(State.Loading)

        val userId = firebaseAuth.currentUser?.uid

        if (userId == null) {
            trySend(State.Error("User not logged in"))
            close() // Close the flow since we can't continue
            return@callbackFlow
        }

        firebaseFirestore.collection(USERS).document(userId)
            .get()
            .addOnSuccessListener {
                val data = it.toObject(userData::class.java)
                if (data != null) {
                    trySend(State.Success(listOf(data))) // wrap in list for backward compatibility
                } else {
                    trySend(State.Error("User data not found"))
                }
            }
            .addOnFailureListener {
                trySend(State.Error(it.localizedMessage ?: "Unknown error"))
            }

        awaitClose { close() }
    }


    override fun getProductById(productId: String): Flow<State<Product>> = callbackFlow {
        trySend(State.Loading)

        firebaseFirestore.collection("Products")
            .whereEqualTo("id", productId) // 🔥 query by field 'id'
            .get()
            .addOnSuccessListener { querySnapshot ->
                val product = querySnapshot.documents.firstOrNull()?.toObject(Product::class.java)
                if (product != null) {
                    trySend(State.Success(product))
                } else {
                    trySend(State.Error("No product found with id: $productId"))
                }
            }
            .addOnFailureListener {
                trySend(State.Error(it.message.toString()))
            }

        awaitClose { close() }
    }

    override fun updateUserData(userData: userData): Flow<State<String>> = callbackFlow {
        trySend(State.Loading)
        val userId = firebaseAuth.currentUser?.uid
        firebaseFirestore.collection(USERS)
            .document(userId.toString())
            .set(userData)
            .addOnSuccessListener {
                trySend(State.Success("User data updated successfully"))

            }.addOnFailureListener {
                trySend(State.Error(it.toString()))
            }
        awaitClose {
            close()
        }


    }

    override fun uploadUserImage(imageUri: Uri): Flow<State<String>> = callbackFlow {
        trySend(State.Loading)  // emit loading state

        val userId = firebaseAuth.currentUser?.uid

        firebaseStorage.reference
            .child("UserImage/${System.currentTimeMillis()}$userId")
            .putFile(imageUri)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { uri ->
                    trySend(State.Success(uri.toString())) // emit firebase download url
                }
            }
            .addOnFailureListener {
                trySend(State.Error(it.toString()))
            }

        awaitClose { close() }
    }

    override fun checkOutData(checkOutData: CheckOutDataModels): Flow<State<String>>  = callbackFlow{

        trySend(State.Loading)
        firebaseFirestore.collection(CheckOutDetails).add(checkOutData)
            .addOnSuccessListener {
                trySend(State.Success("Order Placed Successfully"))

            }
            .addOnFailureListener {
                trySend(State.Error(it.toString()))
            }
        awaitClose{
            close()

            }

    }


}