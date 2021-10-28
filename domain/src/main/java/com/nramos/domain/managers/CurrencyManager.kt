package com.nramos.domain.managers

import com.nramos.domain.model.Rate
import com.nramos.domain.model.Transaction
import kotlin.math.round

class CurrencyManager {

    var rates: List<Rate> = listOf()
    private var foundRate: Double = 1.0
    private var alreadyVisited: HashSet<String> = hashSetOf()

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
        alreadyVisited.clear()
        foundRate = 1.0
        foundRate = getRate(from, to)
        return foundRate
    }

    private fun getRate(from: String, to: String, rate: Double = 1.0): Double {
        calculateRateBetween(from, to)?.let {
            return rate * it
        } ?: run {
            alreadyVisited.add(from)
            rates.forEach {
                if (it.from == from && alreadyVisited.contains(it.to).not()) {
                    foundRate = getRate(from = it.to, to = to, rate = rate * it.rate)
                    if (foundRate != 0.0) {
                        return foundRate
                    }
                }
            }
        }
        return 0.0
    }

    private fun calculateRateBetween(from: String, to: String): Double? {
        rates.forEach {
            if (it.from == from && it.to == to) {
                return it.rate
            }
        }
        return null
    }

}