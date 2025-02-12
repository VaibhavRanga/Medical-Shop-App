package com.vaibhavranga.medicalshopapp.screen.dash

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaibhavranga.medicalshopapp.viewModel.ViewModel

@Composable
fun AddOrderScreen(
    viewModel: ViewModel = hiltViewModel()
) {
    val getAllProductsState = viewModel.getAllProductsState.collectAsStateWithLifecycle()
    val addOrderState = viewModel.addOrderState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var productName by remember { mutableStateOf("") }
    var totalAmount by remember { mutableDoubleStateOf(0.0) }
    var quantity by remember { mutableStateOf("0") }
    var message by remember { mutableStateOf("") }
    var price by remember { mutableDoubleStateOf(0.0) }
    var category by remember { mutableStateOf("") }
    var productId by remember { mutableStateOf("") }
    var dropdown by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text("Place an order")
            OutlinedTextField(
                value = productName,
                onValueChange = {
                    productName = it
                },
                label = {
                    Text(text = "Product Name")
                },
                placeholder = {
                    Text(text = "Please select product name")
                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.getAllProducts()
                            dropdown = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Select product"
                        )
                    }
                    DropdownMenu(
                        expanded = dropdown,
                        onDismissRequest = { dropdown = false },
                        scrollState = rememberScrollState(),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        when {
                            getAllProductsState.value.isLoading -> CircularProgressIndicator()
                            getAllProductsState.value.error != null -> {
                                Toast.makeText(
                                    context,
                                    getAllProductsState.value.error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            getAllProductsState.value.data?.message != null -> {
                                getAllProductsState.value.data?.message!!.forEach { product ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = product.name!!)
                                        },
                                        onClick = {
                                            productName = product.name!!
                                            category = product.category!!
                                            productId = product.productId!!
                                            price = product.price!!
                                            dropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            )
            OutlinedTextField(
                value = quantity,
                onValueChange = {
                    quantity = it
                },
                label = {
                    Text(text = "Quantity")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = message,
                onValueChange = {
                    message = it
                },
                label = {
                    Text(text = "Message")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(text = "Price: $price")
            Text(
                text = "Total amount: ${
                    "%.2f".format(
                        calculateTotalAmount(
                            price = price,
                            quantity = quantity.toInt()
                        )
                    )
                }"
            )
            Button(
                onClick = {
                    totalAmount = calculateTotalAmount(
                        price = price,
                        quantity = quantity.toInt()
                    )
                    viewModel.addOrder(
                        productId = productId,
                        productName = productName,
                        totalAmount = totalAmount,
                        quantity = quantity.toInt(),
                        message = message,
                        price = price,
                        category = category
                    )
                }
            ) {
                Text(text = "Place order")
            }
        }
        when {
            addOrderState.value.isLoading -> CircularProgressIndicator()
            addOrderState.value.error != null -> {
                Toast.makeText(context, addOrderState.value.error, Toast.LENGTH_SHORT).show()
            }

            addOrderState.value.data != null -> {
                Toast.makeText(context, addOrderState.value.data?.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

private fun calculateTotalAmount(price: Double, quantity: Int): Double {
    val total = price * quantity.toDouble()
    return total
}