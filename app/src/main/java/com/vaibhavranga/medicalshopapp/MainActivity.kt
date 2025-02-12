package com.vaibhavranga.medicalshopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vaibhavranga.medicalshopapp.navigation.App
import com.vaibhavranga.medicalshopapp.ui.theme.MedicalShopAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicalShopAppTheme {
                App()
            }
        }
    }
}
