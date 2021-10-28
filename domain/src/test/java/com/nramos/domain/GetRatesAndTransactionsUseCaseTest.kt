package com.nramos.domain

import com.nramos.domain.managers.CurrencyManager
import com.nramos.domain.model.Rate
import com.nramos.domain.model.ResponseError
import com.nramos.domain.model.Transaction
import com.nramos.domain.repositories.RatesRepository
import com.nramos.domain.repositories.TransactionsRepository
import com.nramos.domain.usecases.GetRatesAndTransactions
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRatesAndTransactionsUseCaseTest {

    lateinit var useCaseToTest: GetRatesAndTransactions

    @MockK
    lateinit var mockRatesRepository: RatesRepository

    @MockK
    lateinit var mockTransactionsRepository: TransactionsRepository

    @MockK
    lateinit var mockError: ResponseError

    @MockK
    lateinit var mockTransactions: List<Transaction>

    @MockK
    lateinit var mockRates: List<Rate>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCaseToTest = GetRatesAndTransactions(mockRatesRepository,
            mockTransactionsRepository,
            CurrencyManager())
    }


    @Test
    fun `get rates and fail`() {
        every { runBlocking { mockRatesRepository.getRates() } } answers { Either.Failure(mockError) }
        runBlocking { assert(useCaseToTest.invoke() is Either.Failure) }
    }

    @Test
    fun `get rates and fail on getting transactions`() {
        every { runBlocking { mockRatesRepository.getRates() } } answers { Either.Success(mockRates) }
        every { runBlocking { mockTransactionsRepository.getTransactions() } } answers {
            Either.Failure(mockError)
        }
        runBlocking { assert(useCaseToTest.invoke() is Either.Failure) }
    }

    @Test
    fun `get rates and success on getting transactions`() {
        every { runBlocking { mockRatesRepository.getRates() } } answers { Either.Success(mockRates) }
        every { runBlocking { mockTransactionsRepository.getTransactions() } } answers {
            Either.Success(mockTransactions)
        }
        runBlocking { assert(useCaseToTest.invoke() is Either.Success) }
    }

}
