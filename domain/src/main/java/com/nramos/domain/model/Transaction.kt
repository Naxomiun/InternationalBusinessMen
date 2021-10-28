package com.nramos.domain.model

data class Transaction(
    val sku : String,
    val amount : Double,
    val currency : String
)