package com.nramos.data.services

import com.nramos.data.RATES_ENDPOINT
import com.nramos.data.model.RateDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RatesService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET(RATES_ENDPOINT)
    suspend fun getRates(): Response<List<RateDTO>>

}