package com.himelz.expensetracker.features.home.ui.screens


data class Transaction(
    val name: String,
    val icon: Int,
    val amount: String,
    val amountColor: androidx.compose.ui.graphics.Color,
    val date: String
)