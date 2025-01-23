package com.vaibhavranga.medicalshopapp.api

import com.vaibhavranga.medicalshopapp.model.AddOrderResponse
import com.vaibhavranga.medicalshopapp.model.CreateUserResponse
import com.vaibhavranga.medicalshopapp.model.GetAllProductsResponse
import com.vaibhavranga.medicalshopapp.model.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("createUser")
    suspend fun createUser(
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("pincode") pincode: String
    ): CreateUserResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("getAllProducts")
    suspend fun getAllProducts(): GetAllProductsResponse

    @FormUrlEncoded
    @POST("addOrderDetails")
    suspend fun addOrder(
        @Field("productId") productId: String,
        @Field("userId") userId: String,
        @Field("productName") productName: String,
        @Field("username") username: String,
        @Field("totalAmount") totalAmount: Double,
        @Field("quantity") quantity: Int,
        @Field("message") message: String,
        @Field("price") price: Double,
        @Field("category") category: String
    ): AddOrderResponse
}