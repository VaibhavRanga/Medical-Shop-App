package com.vaibhavranga.medicalshopapp.model

data class User(
    val accountCreationDate: String?,
    val address: String?,
    val email: String?,
    val id: Int?,
    val isApproved: Int?,
    val isBlocked: Int?,
    val name: String?,
    val password: String?,
    val phoneNumber: String?,
    val pincode: String?,
    val userId: String?
)