package com.nramos.internationalbusinessmen.ui.screens.detail.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nramos.domain.managers.CurrencyManager
import com.nramos.domain.model.Rate
import com.nramos.domain.model.Transaction
import com.nramos.internationalbusinessmen.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    @DefaultDispatcher private val defaultCoroutineDispatcher: CoroutineDispatcher, //Injecting dispatchers help to define and handle in what thread are we working.
    private val currencyManager: CurrencyManager,
) : ViewModel() {

    //States control persistent changes in UI
    private val _state = MutableStateFlow<State>(Loading)
    val state: StateFlow<State> = _state

    fun getTotalAmountInEur(transactions: List<Transaction>) {
        viewModelScope.launch {
            val totalAmount = withContext(defaultCoroutineDispatcher) {
                currencyManager.calculateTransactionTotalAmount(
                    transactions = transactions,
                    toCurrency = "EUR"
                )
            }
            _state.emit(Loaded(totalAmount))
        }
    }

}