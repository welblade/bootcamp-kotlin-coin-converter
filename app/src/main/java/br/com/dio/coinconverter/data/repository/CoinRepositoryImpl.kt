package br.com.dio.coinconverter.data.repository

import br.com.dio.coinconverter.core.exceptions.AwesomeApiException
import br.com.dio.coinconverter.data.ErrorResponse
import br.com.dio.coinconverter.data.database.AppDatabase
import br.com.dio.coinconverter.data.model.ExchangeResponseValue
import br.com.dio.coinconverter.data.model.ExchangeValueEntity
import br.com.dio.coinconverter.data.service.AwesomeService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

class CoinRepositoryImpl(
    appDatabase: AppDatabase,
    private val service: AwesomeService
) : CoinRepository {

    private val dao = appDatabase.exchangeDao()

    override suspend fun getExchangeValue(coins: String) = flow {
        try {
            val exchangeValue = service.exchangeValue(coins)
            emit(exchangeValue)
        } catch (exception: HttpException) {
            val json = exception.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
            throw AwesomeApiException(errorResponse.message)
        }
    }

    override suspend fun save(exchange: ExchangeResponseValue) {
        dao.save(ExchangeValueEntity.from(exchange))
    }

    override fun list(): Flow<List<ExchangeResponseValue>> {
        return dao.findAll().map { list ->
            list.map {
                it.toExchangeResponseValue()
            }
        }
    }
}