package com.example.myshoppingapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshoppingapp.common.State
import com.example.myshoppingapp.domain.models.Category
import com.example.myshoppingapp.domain.models.Product
import com.example.myshoppingapp.domain.useCase.GetAllCategoryUseCase
import com.example.myshoppingapp.domain.useCase.GetAllProductUseCase
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
        private val GetAllProduct: GetAllProductUseCase
    ): ViewModel() {

        val _getAllCategoryState = MutableStateFlow(GetAllCategoryState())
    val getAllCategoryState = _getAllCategoryState.asStateFlow()

    val _getAllProductState = MutableStateFlow(GetAllProductState())

    val getAllProductState = _getAllProductState.asStateFlow()


    fun getAllProduct(){

        viewModelScope.launch {
            GetAllProduct.getAllProductUseCase().collectLatest {
                when(it){
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

    fun getAllCategory(){

        viewModelScope.launch {
            GetAllCategory.getAllCategoryUseCase().collectLatest {

                when(it){
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