package com.nramos.internationalbusinessmen.ui.screens.transaction.viewmodel

import com.nramos.domain.model.ResponseError
import com.nramos.domain.model.Transaction

sealed interface State

data class Loaded(val transactions : List<Transaction>) : State

object Loading : State

data class Error(val error : ResponseError) : State