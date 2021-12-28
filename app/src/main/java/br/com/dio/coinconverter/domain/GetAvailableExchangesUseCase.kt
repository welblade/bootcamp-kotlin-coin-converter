package br.com.dio.coinconverter.domain

import br.com.dio.coinconverter.core.UseCase
import br.com.dio.coinconverter.data.model.AvailableExchange
import br.com.dio.coinconverter.data.repository.AvailableExchangeRepository
import kotlinx.coroutines.flow.Flow

class GetAvailableExchangesUseCase(
    private val repository: AvailableExchangeRepository
) : UseCase<String, List<AvailableExchange>>() {
    override suspend fun execute(param: String): Flow<List<AvailableExchange>> {
        return repository.getExchangesAvailableTo(param)
    }
}