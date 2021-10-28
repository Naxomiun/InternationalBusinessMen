package com.nramos.data.services

import com.nramos.data.TRANSACTIONS_ENDPOINT
import com.nramos.data.model.TransactionDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface TransactionsService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET(TRANSACTIONS_ENDPOINT)
    suspend fun getTransactions(): Response<List<TransactionDTO>>

}