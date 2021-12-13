package br.com.dio.coinconverter.data

data class ErrorResponse(
    val status: Long,
    val code: String,
    val message: String
)
