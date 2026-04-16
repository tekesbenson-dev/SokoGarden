package com.example.sokogarden

data class Product(
    val product_id: Int,
    val product_name: String,
    val product_description: String?,
    val product_cost: Int,
    val product_photo: String?
)
