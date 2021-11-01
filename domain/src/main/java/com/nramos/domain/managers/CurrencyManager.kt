package com.nramos.domain.managers

import com.nramos.domain.model.Rate
import com.nramos.domain.model.Transaction
import kotlin.math.round

//we could have made one interface if we needed different implementations depending on the project
class CurrencyManager {

    var rates: List<Rate> = listOf()

    fun calculateTransactionTotalAmount(
        transactions: List<Transaction>,
        toCurrency: String,
    ): Double {
        var totalAmount = 0.0
        transactions.forEach {
            totalAmount += if (it.currency == toCurrency) {
                it.amount
            } else {
                calculateTransactionRate(
                    from = it.currency,
                    to = toCurrency
                ) * it.amount
            }
        }
        return totalAmount
    }

    private fun calculateTransactionRate(from: String, to: String): Double {
        return getRate(from, to) ?: 0.0
    }

    private fun getDirectRate(from: String, to: String): Double? {
        rates.forEach {
            if (it.from == from && it.to == to) {
                return it.rate
            }
        }
        return null
    }

    private fun getRate(
        from: String,
        to: String,
        rate: Double = 1.0,
        converted: HashSet<String> = hashSetOf(),
    ): Double? {
        getDirectRate(from, to)?.let {
            return rate * it
        } ?: run {
            rates.forEach {
                if (it.from == from && (it.to in converted).not()) {
                    converted.add(from)
                    getRate(
                        from = it.to,
                        to = to,
                        rate = rate * it.rate,
                        converted = converted
                    )?.let { newRate ->
                        return newRate
                    }
                }
            }
        }
        return null
    }

}