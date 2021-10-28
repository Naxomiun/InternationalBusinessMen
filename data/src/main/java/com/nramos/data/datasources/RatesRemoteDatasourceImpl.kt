package com.nramos.data.datasources

import com.nramos.data.model.toDomain
import com.nramos.data.services.RatesService
import com.nramos.domain.Either
import com.nramos.domain.datasources.RatesRemoteDatasource
import com.nramos.domain.eitherFailure
import com.nramos.domain.eitherSuccess
import com.nramos.domain.model.Rate
import com.nramos.domain.model.ResponseError
import javax.inject.Inject

class RatesRemoteDatasourceImpl @Inject constructor(
    private val ratesService: RatesService,
) : RatesRemoteDatasource {

    override suspend fun getRates(): Either<List<Rate>, ResponseError> {
        return try {
            val response = ratesService.getRates()
            if (response.isSuccessful)
                response.body()?.let { list ->
                    eitherSuccess(list.map { it.toDomain() })
                } ?: eitherFailure(ResponseError.Generic("Rates Error: empty response"))
            else eitherFailure(ResponseError.Generic("Rates Error: not successful"))

        } catch (exception: Exception) {
            eitherFailure(ResponseError.Network)
        }
    }

}