package com.example.myshoppingapp.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshoppingapp.common.State
import com.example.myshoppingapp.domain.models.Category
import com.example.myshoppingapp.domain.models.Product
import com.example.myshoppingapp.domain.models.userData
import com.example.myshoppingapp.domain.useCase.GetAllCategoryUseCase
import com.example.myshoppingapp.domain.useCase.GetAllProductUseCase
import com.example.myshoppingapp.domain.useCase.GetProductByIdUseCase
import com.example.myshoppingapp.domain.useCase.GetUserDataUseCase
import com.example.myshoppingapp.domain.useCase.UpdateUserDataUseCase
import com.example.myshoppingapp.domain.useCase.UploadUserImageUseCase
import com.example.myshoppingapp.domain.useCase.UserLoginWithEmailAndPasswordUseCase
import com.example.myshoppingapp.domain.useCase.UserRegisterWithEmailAndPasswordUseCase
import com.google.firebase.firestore.core.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyViewModel
@Inject constructor(
    private val GetAllCategory: GetAllCategoryUseCase,
    private val GetAllProduct: GetAllProductUseCase,
    private val userRegisterWithEmailAndPasswordUseCase: UserRegisterWithEmailAndPasswordUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val userLoginWithEmailAndPasswordUseCase: UserLoginWithEmailAndPasswordUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val uploadUserImageUseCase: UploadUserImageUseCase
) : ViewModel() {

    val _getAllCategoryState = MutableStateFlow(GetAllCategoryState())
    val getAllCategoryState = _getAllCategoryState.asStateFlow()

    val _getAllProductState = MutableStateFlow(GetAllProductState())

    val getAllProductState = _getAllProductState.asStateFlow()
    val _userRegisterState = MutableStateFlow(UserRegisterState())
    val userRegisterState = _userRegisterState.asStateFlow()

    val _getUserDataState = MutableStateFlow(GetUserDataState())

    val getUserDataState = _getUserDataState.asStateFlow()

    val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()


    val _getProductByIdState = MutableStateFlow(GetProductByIdState())
    val getProductByIdState = _getProductByIdState.asStateFlow()

    val _updateUserDataState = MutableStateFlow(UpdateUserDataState())
    val updateUserDataState = _updateUserDataState.asStateFlow()

    val _uploadUserImageState = MutableStateFlow(UploadUserImageState())
    val uploadUserImageState = _uploadUserImageState.asStateFlow()



    fun updateUserData(userData: userData){
        viewModelScope.launch {
            updateUserDataUseCase.updateUserDataUseCase(userData).collectLatest {
                when(it){
                    is State.Success -> {
                        _updateUserDataState.value = UpdateUserDataState(data = it.data)
                    }
                    is State.Error -> {
                        _updateUserDataState.value = UpdateUserDataState(error = it.error)
                    }
                    is State.Loading -> {
                        _updateUserDataState.value = UpdateUserDataState(isLoading = true)
                    }
                }

            }
        }
    }


    fun uploadUserImage(imageUri: Uri){
        viewModelScope.launch {

            uploadUserImageUseCase.uploadUserImageUseCase(imageUri).collectLatest {
                when(it) {
                    is State.Success -> {
                        _uploadUserImageState.value = UploadUserImageState(data = it.data)
                    }

                    is State.Error -> {
                        _uploadUserImageState.value = UploadUserImageState(error = it.error)
                    }

                    is State.Loading -> {
                        _uploadUserImageState.value = UploadUserImageState(isLoading = true)
                    }
                }

            }
        }
    }


    fun getProductById(productId: String) {

        viewModelScope.launch {
            getProductByIdUseCase.getProductByIdUseCase(productId = productId).collectLatest {
                when (it) {
                    is State.Success -> {
                        _getProductByIdState.value = GetProductByIdState(data = it.data)
                    }

                    is State.Error -> {
                        _getProductByIdState.value = GetProductByIdState(error = it.error)
                    }

                    is State.Loading -> {
                        _getProductByIdState.value = GetProductByIdState(isLoading = true)
                    }
                }
            }
        }
    }


    fun login(email: String, password: String) {
        viewModelScope.launch {
            userLoginWithEmailAndPasswordUseCase.userLoginWithEmailAndPasswordUseCase(
                email,
                password
            ).collectLatest {
                when (it) {
                    is State.Success -> {
                        _loginState.value = LoginState(data = it.data)
                    }

                    is State.Error -> {
                        _loginState.value = LoginState(error = it.error)
                    }

                    is State.Loading -> {
                        _loginState.value = LoginState(isLoading = true)
                    }
                }

            }

        }
    }


    fun getUserData() {
        viewModelScope.launch {
            getUserDataUseCase.getUserDataUseCase().collectLatest {
                when (it) {
                    is State.Success -> {
                        _getUserDataState.value = GetUserDataState(data = it.data)
                    }

                    is State.Error -> {
                        _getUserDataState.value = GetUserDataState(error = it.error)
                    }

                    is State.Loading -> {
                        _getUserDataState.value = GetUserDataState(isLoading = true)
                    }
                }
            }

        }
    }


    fun registerUser(userData: userData) {
        viewModelScope.launch {
            userRegisterWithEmailAndPasswordUseCase.userRegisterWithEmailAndPasswordUseCase(userData)
                .collectLatest {
                    when (it) {
                        is State.Success -> {
                            _userRegisterState.value = UserRegisterState(userData = it.data)
                        }

                        is State.Error -> {
                            _userRegisterState.value = UserRegisterState(error = it.error)
                        }

                        is State.Loading -> {
                            _userRegisterState.value = UserRegisterState(isLoading = true)
                        }
                    }
                }
        }
    }


    fun getAllProduct() {

        viewModelScope.launch {
            GetAllProduct.getAllProductUseCase().collectLatest {
                when (it) {
                    is State.Success -> {
                        _getAllProductState.value = GetAllProductState(data = it.data)
                    }

                    is State.Error -> {
                        _getAllProductState.value = GetAllProductState(error = it.error)
                    }

                    is State.Loading -> {
                        _getAllProductState.value = GetAllProductState(isLoading = true)
                    }
                }
            }
        }
    }

    fun getAllCategory() {

        viewModelScope.launch {
            GetAllCategory.getAllCategoryUseCase().collectLatest {

                when (it) {
                    is State.Success -> {
                        _getAllCategoryState.value = GetAllCategoryState(data = it.data)
                    }

                    is State.Error -> {
                        _getAllCategoryState.value = GetAllCategoryState(error = it.error)
                    }

                    is State.Loading -> {
                        _getAllCategoryState.value = GetAllCategoryState(isLoading = true)
                    }
                }
            }

        }
    }


}

data class GetAllCategoryState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: List<Category> = emptyList()

)

data class GetAllProductState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: List<Product> = emptyList()
)

data class UserRegisterState(
    val error: String = "",
    val isLoading: Boolean = false,
    val userData: String? = null

)

data class GetUserDataState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: List<userData> = emptyList()
)

data class LoginState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: String? = null

)

data class GetProductByIdState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: Product? = null

)

data class UpdateUserDataState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: String? = null
)

data class UploadUserImageState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: String? = null

)