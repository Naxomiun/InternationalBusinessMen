package com.nramos.domain.repositories

import com.nramos.domain.Either
import com.nramos.domain.model.Rate
import com.nramos.domain.model.ResponseError

interface RatesRepository {

    suspend fun getRates() : Either<List<Rate>, ResponseError>

}