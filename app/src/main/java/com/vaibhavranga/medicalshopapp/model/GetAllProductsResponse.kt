package com.vaibhavranga.medicalshopapp.model

data class GetAllProductsResponse(
    val message: List<Product>?,
    val status: Int?
)