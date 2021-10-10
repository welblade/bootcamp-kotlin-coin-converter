package br.com.dio.coinconverter.data.service

import retrofit2.http.GET
import retrofit2.http.Path

interface AwesomeService {
    @GET("/last/{coins}")
    suspend fun exchangeValue(@Path("coins") coins: String)
}