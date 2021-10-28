package com.nramos.domain.datasources

import com.nramos.domain.Either
import com.nramos.domain.model.Rate
import com.nramos.domain.model.ResponseError

interface RatesRemoteDatasource {

    suspend fun getRates() : Either<List<Rate>, ResponseError>

}