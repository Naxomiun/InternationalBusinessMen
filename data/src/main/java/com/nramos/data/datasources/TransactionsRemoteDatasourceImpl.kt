package com.nramos.data.datasources

import com.nramos.data.model.toDomain
import com.nramos.data.services.TransactionsService
import com.nramos.domain.Either
import com.nramos.domain.datasources.TransactionsRemoteDatasource
import com.nramos.domain.eitherFailure
import com.nramos.domain.eitherSuccess
import com.nramos.domain.model.ResponseError
import com.nramos.domain.model.Transaction
import javax.inject.Inject

class TransactionsRemoteDatasourceImpl @Inject constructor(
    private val transactionsService: TransactionsService,
) : TransactionsRemoteDatasource {

    override suspend fun getTransactions(): Either<List<Transaction>, ResponseError> {
        return try {
            val response = transactionsService.getTransactions()
            if (response.isSuccessful)
                response.body()?.let { list ->
                    eitherSuccess(list.map { it.toDomain() })
                } ?: eitherFailure(ResponseError.Generic("Transactions Error: empty response"))
            else eitherFailure(ResponseError.Generic("Transactions Error: not successful"))

        } catch (exception: Exception) {
            eitherFailure(ResponseError.Network)
        }
    }

}