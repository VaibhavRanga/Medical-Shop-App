package com.vaibhavranga.medicalshopapp.screen.dash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaibhavranga.medicalshopapp.viewModel.ViewModel

@Composable
fun ProfileScreen(
    onSignOutClick: () -> Unit,
    viewModel: ViewModel = hiltViewModel()
) {
    val userDetailsFromPrefs by viewModel.userDetailsFromPrefs.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        viewModel.getUserDetailsFromPrefs()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = "Name: ${userDetailsFromPrefs?.name}")
            Text(text = "Email: ${userDetailsFromPrefs?.email}")
            Text(text = "Address: ${userDetailsFromPrefs?.address}")
            Text(text = "Pincode: ${userDetailsFromPrefs?.pincode}")
            Text(text = "Phone Number: ${userDetailsFromPrefs?.phoneNumber}")
            Text(text = "Account Created on: ${userDetailsFromPrefs?.accountCreationDate}")
        }
        Button(
            onClick = onSignOutClick,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
        ) {
            Text("Sign out")
        }
    }
}