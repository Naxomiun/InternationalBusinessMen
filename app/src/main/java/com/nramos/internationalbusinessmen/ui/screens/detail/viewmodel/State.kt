package com.nramos.internationalbusinessmen.ui.screens.detail.viewmodel

import com.nramos.domain.model.ResponseError

sealed interface State

data class Loaded(val totalAmount : Double) : State

object Loading : State

data class Error(val error : ResponseError) : State