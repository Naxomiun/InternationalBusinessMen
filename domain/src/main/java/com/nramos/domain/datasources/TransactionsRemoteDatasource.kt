package com.nramos.domain.datasources

import com.nramos.domain.Either
import com.nramos.domain.model.ResponseError
import com.nramos.domain.model.Transaction

interface TransactionsRemoteDatasource {

    suspend fun getTransactions() : Either<List<Transaction>, ResponseError>

}