package com.nramos.domain.model

sealed class ResponseError(val message: CharSequence)  {
    data class Generic(val errorMessage : CharSequence) : ResponseError(message = errorMessage)
    object Network : ResponseError(message = "Connection error")
}
