package com.vaibhavranga.medicalshopapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhavranga.medicalshopapp.common.Results
import com.vaibhavranga.medicalshopapp.model.AddOrderResponse
import com.vaibhavranga.medicalshopapp.model.CreateUserResponse
import com.vaibhavranga.medicalshopapp.model.GetAllProductsResponse
import com.vaibhavranga.medicalshopapp.model.LoginResponse
import com.vaibhavranga.medicalshopapp.model.SavedUserDataModel
import com.vaibhavranga.medicalshopapp.prefDataStore.MyPreferences
import com.vaibhavranga.medicalshopapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val prefs: MyPreferences,
    private val repository: Repository
) : ViewModel() {
    private val _userDetailsFromPrefs = MutableStateFlow<SavedUserDataModel?>(null)
    val userDetailsFromPrefs = _userDetailsFromPrefs.asStateFlow()

    private val _createUser = MutableStateFlow(CreateUserState())
    val createUser = _createUser.asStateFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val _getAllProductsState = MutableStateFlow(GetAllProductsState())
    val getAllProductsState = _getAllProductsState.asStateFlow()

    private val _addOrderState = MutableStateFlow(AddOrderState())
    val addOrderState = _addOrderState.asStateFlow()

    suspend fun getUserDetailsFromPrefs() {
        prefs.getUserFromPrefs.collect {
            _userDetailsFromPrefs.value = it
            Log.d("TAG", "getUserIdFromPrefs in view model: ${_userDetailsFromPrefs.value?.userId}")
        }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            prefs.clearUserPrefDetails()
        }
    }

    fun createUser(
        name: String,
        password: String,
        email: String,
        address: String,
        phoneNumber: String,
        pincode: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createUser(name, password, email, address, phoneNumber, pincode)
                .collect { response ->
                    when (response) {
                        is Results.Loading -> _createUser.value = CreateUserState(isLoading = true)
                        is Results.Error -> _createUser.value =
                            CreateUserState(isLoading = false, error = response.message)

                        is Results.Success -> _createUser.value =
                            CreateUserState(isLoading = false, data = response.data)
                    }
                }
        }
    }

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.login(email, password).collect { response ->
                when (response) {
                    is Results.Loading -> _loginState.value = LoginState(isLoading = true)
                    is Results.Error -> _loginState.value =
                        LoginState(isLoading = false, error = response.message)

                    is Results.Success -> {
                        _loginState.value = LoginState(isLoading = false, data = response.data)
                        if ((response.data.message?.isApproved != 0) && (response.data.message?.isBlocked != 1)) {
                            prefs.saveUserDetailsToPrefs(response.data.message!!)
                        }
                    }
                }
            }
        }
    }

    fun clearLoginState() {
        _loginState.update {
            it.copy(
                isLoading = false,
                error = "",
                data = null
            )
        }
    }

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllProducts().collect { response ->
                when (response) {
                    is Results.Loading -> _getAllProductsState.value =
                        GetAllProductsState(isLoading = true)

                    is Results.Error -> _getAllProductsState.value =
                        GetAllProductsState(isLoading = false, error = response.message)

                    is Results.Success -> _getAllProductsState.value =
                        GetAllProductsState(isLoading = false, data = response.data)
                }
            }
        }
    }

    fun addOrder(
        productId: String,
        productName: String,
        totalAmount: Double,
        quantity: Int,
        message: String,
        price: Double,
        category: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOrder(
                productId = productId,
                userId = _userDetailsFromPrefs.value?.userId!!,
                productName = productName,
                username = _userDetailsFromPrefs.value?.name!!,
                totalAmount = totalAmount,
                quantity = quantity,
                message = message,
                price = price,
                category = category
            ).collect { response ->
                when (response) {
                    is Results.Loading -> _addOrderState.value = AddOrderState(isLoading = true)
                    is Results.Error -> _addOrderState.value =
                        AddOrderState(isLoading = false, error = response.message)

                    is Results.Success -> _addOrderState.value =
                        AddOrderState(isLoading = false, data = response.data)
                }
            }
        }
    }
}

data class CreateUserState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: CreateUserResponse? = null
)

data class LoginState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: LoginResponse? = null
)

data class GetAllProductsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: GetAllProductsResponse? = null
)

data class AddOrderState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: AddOrderResponse? = null
)