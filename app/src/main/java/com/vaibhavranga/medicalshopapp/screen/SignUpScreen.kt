package com.vaibhavranga.medicalshopapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vaibhavranga.medicalshopapp.navigation.Authentication
import com.vaibhavranga.medicalshopapp.viewModel.ViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: ViewModel = hiltViewModel()
) {
    val state by viewModel.createUser.collectAsStateWithLifecycle()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Text(text = "Sign Up")
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text(text = "Phone Number") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text(text = "Address") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = pincode,
                onValueChange = { pincode = it },
                label = { Text(text = "Pincode") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Button(
                onClick = {
                    if (
                        email.isNotBlank()
                        && password.isNotBlank()
                        && name.isNotBlank()
                        && pincode.isNotBlank()
                        && phoneNumber.isNotBlank()
                        && address.isNotBlank()
                    ) {
                        viewModel.createUser(
                            name = name,
                            password = password,
                            email = email,
                            address = address,
                            phoneNumber = phoneNumber,
                            pincode = pincode
                        )
                    } else {
                        Toast.makeText(
                            context,
                            "Please enter all the details",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            ) {
                Text(text = "Sign Up")
            }
        }
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error.isNotBlank() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.error)
                }
            }

            state.data?.message != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Dialog(
                        onDismissRequest = {},
                        properties = DialogProperties(
                            dismissOnClickOutside = true,
                            dismissOnBackPress = true,
                            usePlatformDefaultWidth = false
                        )
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .height(200.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                Text(text = "Login to continue. Login is subject to approval of your account by admin.")
                                Button(
                                    onClick = {
                                        navController.navigate(Authentication.SignInScreenRoute)
                                    }
                                ) {
                                    Text(text = "Go to Login screen")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}