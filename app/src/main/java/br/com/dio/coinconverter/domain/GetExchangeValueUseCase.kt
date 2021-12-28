package br.com.dio.coinconverter.domain

import br.com.dio.coinconverter.core.UseCase
import br.com.dio.coinconverter.data.model.ExchangeResponse
import br.com.dio.coinconverter.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class GetExchangeValueUseCase(
    private val repository: CoinRepository
) : UseCase<String, ExchangeResponse>() {
    override suspend fun execute(param: String): Flow<ExchangeResponse> {
        return repository.getExchangeValue(param)
    }
}