package com.nramos.data.model

import com.nramos.domain.model.Rate
import com.nramos.domain.model.Transaction

fun RateDTO.toDomain() = Rate(
    from, to, rate.toDouble()
)

fun TransactionDTO.toDomain() = Transaction(
    sku, amount.toDouble(), currency
)