package com.vaibhavranga.medicalshopapp.repository

import com.vaibhavranga.medicalshopapp.api.ApiService
import com.vaibhavranga.medicalshopapp.common.Results
import com.vaibhavranga.medicalshopapp.model.AddOrderResponse
import com.vaibhavranga.medicalshopapp.model.CreateUserResponse
import com.vaibhavranga.medicalshopapp.model.GetAllProductsResponse
import com.vaibhavranga.medicalshopapp.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    fun createUser(
        name: String,
        password: String,
        email: String,
        address: String,
        phoneNumber: String,
        pincode: String
    ): Flow<Results<CreateUserResponse>> = flow {
        emit(Results.Loading)

        try {
            val response = apiService.createUser(
                name,
                password,
                email,
                address,
                phoneNumber,
                pincode
            )
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    fun login(
        email: String,
        password: String
    ): Flow<Results<LoginResponse>> = flow {
        emit(Results.Loading)

        try {
            val response = apiService.login(email, password)
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    fun getAllProducts(): Flow<Results<GetAllProductsResponse>> = flow {
        emit(Results.Loading)

        try {
            val response = apiService.getAllProducts()
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    fun addOrder(
        productId: String,
        userId: String,
        productName: String,
        username: String,
        totalAmount: Double,
        quantity: Int,
        message: String,
        price: Double,
        category: String
    ): Flow<Results<AddOrderResponse>> = flow {
        emit(Results.Loading)

        try {
            val response = apiService.addOrder(
                productId = productId,
                userId = userId,
                productName = productName,
                username = username,
                totalAmount = totalAmount,
                quantity = quantity,
                message = message,
                price = price,
                category = category
            )
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }
}