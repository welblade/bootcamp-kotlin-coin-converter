package br.com.dio.coinconverter.domain

import br.com.dio.coinconverter.core.UseCase
import br.com.dio.coinconverter.data.model.Coin
import br.com.dio.coinconverter.data.repository.CoinListRepository
import kotlinx.coroutines.flow.Flow

class ListCoinUseCase(
    private val coinListRepository: CoinListRepository
) : UseCase.NoParam<List<Coin>>() {
    override suspend fun execute(): Flow<List<Coin>> {
        return coinListRepository.getCoinList()
    }
}