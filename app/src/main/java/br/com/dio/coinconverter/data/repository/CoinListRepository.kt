package br.com.dio.coinconverter.data.repository

import br.com.dio.coinconverter.data.model.Coin
import kotlinx.coroutines.flow.Flow

interface CoinListRepository {
    suspend fun getCoinList():Flow<List<Coin>>
}