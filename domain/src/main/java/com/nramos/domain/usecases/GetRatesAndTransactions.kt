package com.nramos.domain.usecases

import com.nramos.domain.*
import com.nramos.domain.managers.CurrencyManager
import com.nramos.domain.model.Rate
import com.nramos.domain.model.ResponseError
import com.nramos.domain.model.Transaction
import com.nramos.domain.repositories.RatesRepository
import com.nramos.domain.repositories.TransactionsRepository
import javax.inject.Inject

class GetRatesAndTransactions @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val transactionsRepository: TransactionsRepository,
    private val currencyManager: CurrencyManager
)  {

    suspend operator fun invoke() : Either<List<Transaction>, ResponseError> {
        ratesRepository.getRates().onSuccess {
            currencyManager.rates = it
        }.onFailure {
            return eitherFailure(it)
        }

        transactionsRepository.getTransactions().onSuccess {
            return eitherSuccess(it)
        }.onFailure {
            return eitherFailure(it)
        }

        return eitherFailure(ResponseError.Generic("Unknown error"))
    }

}