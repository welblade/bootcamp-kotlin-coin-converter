package br.com.dio.coinconverter.data.repository

import br.com.dio.coinconverter.core.exceptions.JsonAssetException
import br.com.dio.coinconverter.data.model.Coin
import kotlinx.coroutines.flow.flow

class CoinListRepositoryImpl : CoinListRepository{
    override suspend fun getCoinList() = flow {
        try {
            val coinList = Coin.values().toList()
            emit(coinList)
        }catch (e: Exception){
            throw JsonAssetException(e.localizedMessage)
        }
    }
}