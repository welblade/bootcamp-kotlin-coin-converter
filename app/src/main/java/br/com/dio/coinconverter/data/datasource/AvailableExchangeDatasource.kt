package br.com.dio.coinconverter.data.datasource

import br.com.dio.coinconverter.data.model.AvailableExchange

interface AvailableExchangeDatasource {
    suspend fun getExchangesAvailableTo(coin: String):List<AvailableExchange>
}