package br.com.dio.coinconverter.data.repository

import br.com.dio.coinconverter.data.model.Coin
import kotlinx.coroutines.flow.flow

class CoinListRepositoryImpl : CoinListRepository{
    override suspend fun getCoinList() = flow {
        val coinList = Coin.values().toList()
        emit(coinList)
    }
}