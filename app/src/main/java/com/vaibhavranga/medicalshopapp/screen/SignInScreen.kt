package com.vaibhavranga.medicalshopapp.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vaibhavranga.medicalshopapp.navigation.Routes
import com.vaibhavranga.medicalshopapp.ui.theme.MedicalShopAppTheme
import com.vaibhavranga.medicalshopapp.viewModel.ViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    onSignInSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ViewModel = hiltViewModel()
) {
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    var showUnapprovedDialog by remember { mutableStateOf(false) }
    var showBlockedDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        SignInFields(
            onSignUpButtonClick = {
                navController.navigate(Routes.SignUpScreenRoute)
            },
            onSignInButtonClick = { email, password ->
                viewModel.login(email, password)
            },
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        )
        if (showUnapprovedDialog) {
            ShowDialog(text = "Your account is pending approval by the admin", onPopupDismissAction = { showUnapprovedDialog = false})
        }
        if (showBlockedDialog) {
            ShowDialog(text = "Your account has been blocked by the admin", onPopupDismissAction = { showBlockedDialog = false})
        }
        when {
            loginState.isLoading -> {
                CircularProgressIndicator()
            }
            loginState.error.isNotBlank() -> {
                Toast.makeText(context, loginState.error, Toast.LENGTH_SHORT).show()
                viewModel.clearLoginState()
            }
            loginState.data != null -> {
                if (loginState.data!!.message?.isBlocked == 1) {
                    showBlockedDialog = true
                    viewModel.clearLoginState()
                } else if (loginState.data!!.message?.isApproved == 0) {
                    showUnapprovedDialog = true
                    viewModel.clearLoginState()
                } else {
                    onSignInSuccess()
                    viewModel.clearLoginState()
                }
            }
        }
    }
}

@Composable
fun SignInFields(
    onSignUpButtonClick: () -> Unit,
    onSignInButtonClick: (email: String, password: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(text = "Sign In")
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = "Email")
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = "Password")
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = {
                onSignInButtonClick(email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign In")
        }
        TextButton(
            onClick = onSignUpButtonClick
        ) {
            Text(
                text = "Sign up here",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ShowDialog(text: String, onPopupDismissAction: () -> Unit) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = onPopupDismissAction,
        properties = PopupProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(space = 32.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(200.dp)
                .background(color = Color.Cyan, shape = RoundedCornerShape(16.dp))
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
        ) {
            Text(text = text)
            Button(
                onClick = onPopupDismissAction
            ) {
                Text("Ok")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignInScreenPreview() {
    MedicalShopAppTheme {
        SignInFields(
            onSignUpButtonClick = {},
            onSignInButtonClick = { email, password -> }
        )
    }
}