package com.nramos.internationalbusinessmen.ui.screens.transaction.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nramos.domain.managers.CurrencyManager
import com.nramos.domain.model.Transaction
import com.nramos.domain.onFailure
import com.nramos.domain.onSuccess
import com.nramos.domain.usecases.GetRatesAndTransactions
import com.nramos.internationalbusinessmen.di.DefaultDispatcher
import com.nramos.internationalbusinessmen.di.IoDispatcher
import com.nramos.internationalbusinessmen.ui.screens.transaction.viewmodel.Error
import com.nramos.internationalbusinessmen.ui.screens.transaction.viewmodel.Loaded
import com.nramos.internationalbusinessmen.ui.screens.transaction.viewmodel.Loading
import com.nramos.internationalbusinessmen.ui.screens.transaction.viewmodel.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @IoDispatcher private val ioCoroutineDispatcher: CoroutineDispatcher, //Injecting dispatchers help to define and handle in what thread are we working.
    @DefaultDispatcher private val defaultCoroutineDispatcher: CoroutineDispatcher,
    private val getRatesAndTransactions: GetRatesAndTransactions,
) : ViewModel() {

    //States control persistent changes in UI
    private val _state = MutableStateFlow<State>(Loading)
    val state: StateFlow<State> = _state

    //Events control volatile changes in UI (toasts, snackbars, navigation, dialogs etc.).
    //Channels ensure that events are not called twice in case of config changes or activity recreation.
    private val _event = Channel<Event>()
    val event: Flow<Event> = _event.receiveAsFlow()

    private var transactions = listOf<Transaction>()
    private var filteredTransactions = listOf<Transaction>()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val response = withContext(ioCoroutineDispatcher) {
                getRatesAndTransactions()
            }

            response.onSuccess {
                transactions = it
                _state.emit(Loaded(it))
            }.onFailure {
                _state.emit(Error(it))
            }
        }
    }

    fun navigateToDetail(sku : String) {
        viewModelScope.launch {
            filteredTransactions = withContext(defaultCoroutineDispatcher) {
                transactions.filter { it.sku == sku }
            }
            _event.send(NavigateToDetail)
        }
    }

    fun getFilteredList() = filteredTransactions

}