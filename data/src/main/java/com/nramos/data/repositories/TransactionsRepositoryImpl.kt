package com.nramos.data.repositories


import com.nramos.domain.Either
import com.nramos.domain.datasources.RatesRemoteDatasource
import com.nramos.domain.datasources.TransactionsRemoteDatasource
import com.nramos.domain.model.Rate
import com.nramos.domain.model.ResponseError
import com.nramos.domain.model.Transaction
import com.nramos.domain.repositories.RatesRepository
import com.nramos.domain.repositories.TransactionsRepository

import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val remoteTransactionDatasource: TransactionsRemoteDatasource
    //TODO futurible: create and inject localDatasource to load cached results if any
) : TransactionsRepository {

    override suspend fun getTransactions(): Either<List<Transaction>, ResponseError> = remoteTransactionDatasource.getTransactions()

}