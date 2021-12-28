package br.com.dio.coinconverter.data.repository

import br.com.dio.coinconverter.data.datasource.AvailableExchangeDatasource
import kotlinx.coroutines.flow.flow

class AvailableExchangeRepositoryImpl(
    private val datasource: AvailableExchangeDatasource
) : AvailableExchangeRepository {
    override suspend fun getExchangesAvailableTo(coin: String) = flow {
        val exchanges = datasource.getExchangesAvailableTo(coin)
        emit(exchanges)
    }
}