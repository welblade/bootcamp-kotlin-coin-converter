package br.com.dio.coinconverter.data.repository

import br.com.dio.coinconverter.data.model.AvailableExchange
import kotlinx.coroutines.flow.Flow

interface AvailableExchangeRepository {
    suspend fun getExchangesAvailableTo(coin: String): Flow<List<AvailableExchange>>
}