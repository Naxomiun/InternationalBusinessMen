package com.nramos.domain

import com.nramos.domain.managers.CurrencyManager
import com.nramos.domain.model.Rate
import com.nramos.domain.model.Transaction
import org.junit.Before
import org.junit.Test
import kotlin.math.round

class CurrencyManagerTest {

    lateinit var currencyManager: CurrencyManager

    val mockTransactionsSampleData = listOf(
        Transaction("T2006", 10.00, "USD"),
        Transaction("T2006", 7.63, "EUR"),
        Transaction("XXXXX", 0.00, "EUR"),
        Transaction("XXXXX", 0.00, "EUR"),
        Transaction("XXXXX", 0.00, "EUR")

    )

    val mockRatesSampleData = listOf(
        Rate(from = "EUR", to = "USD", rate = 1.359),
        Rate(from = "CAD", to = "EUR", rate = 0.732),
        Rate(from = "USD", to = "EUR", rate = 0.736),
        Rate(from = "EUR", to = "CAD", rate = 1.166),
    )

    val mockSingleTransaction = listOf(
        Transaction("test", 10.0, "USD")
    )

    @Before
    fun setup() {
        currencyManager = CurrencyManager()
        currencyManager.rates = mockRatesSampleData
    }

    @Test
    fun `calculate correct amount of sample data`() {
        /*Extracted from challenge:
            'For example, for the sample data, the total sum for the product T2006 should be 14,99 EUR.'
        */
        assert(round(currencyManager.calculateTransactionTotalAmount(mockTransactionsSampleData.filter { it.sku == "T2006" },
            "EUR")) == round(14.99))
    }

    @Test
    fun `calculate correct single transaction not related`() {
        /* 10 USD to CAD
           10 USD * 0.736 = 7.36 EUR
           7.36 EUR * 1.166 = 8.58176 CAD
         */
        assert(round(currencyManager.calculateTransactionTotalAmount(mockSingleTransaction,
            "CAD")) == round(8.58176))
    }


}
