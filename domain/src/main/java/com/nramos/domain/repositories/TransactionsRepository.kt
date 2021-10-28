package com.nramos.domain.repositories

import com.nramos.domain.Either
import com.nramos.domain.model.Rate
import com.nramos.domain.model.ResponseError
import com.nramos.domain.model.Transaction

interface TransactionsRepository {

    suspend fun getTransactions() : Either<List<Transaction>, ResponseError>

}