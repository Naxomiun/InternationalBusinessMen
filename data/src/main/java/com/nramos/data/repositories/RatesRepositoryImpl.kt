package com.nramos.data.repositories


import com.nramos.domain.Either
import com.nramos.domain.datasources.RatesRemoteDatasource
import com.nramos.domain.model.Rate
import com.nramos.domain.model.ResponseError
import com.nramos.domain.repositories.RatesRepository

import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val ratesRemoteDatasource: RatesRemoteDatasource
    //TODO futurible: create and inject localDatasource to load cached results if any
) : RatesRepository {

    override suspend fun getRates(): Either<List<Rate>, ResponseError> = ratesRemoteDatasource.getRates()

}