package com.vaibhavranga.medicalshopapp.model

data class SavedUserDataModel(
    val accountCreationDate: String?,
    val address: String?,
    val email: String?,
    val isApproved: Int?,
    val isBlocked: Int?,
    val name: String?,
    val phoneNumber: String?,
    val pincode: String?,
    val userId: String?
)