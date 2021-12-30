package br.com.dio.coinconverter.data.repository

import br.com.dio.coinconverter.core.exceptions.AwesomeApiException
import br.com.dio.coinconverter.data.ErrorResponse
import br.com.dio.coinconverter.data.service.AwesomeService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class CoinRepositoryImpl(
    private val service: AwesomeService
) : CoinRepository {

    override suspend fun getExchangeValue(coins: String) = flow {
        try {
            val exchangeValue = service.exchangeValue(coins)
            emit(exchangeValue)
        } catch (exception: HttpException) {
            withContext(Dispatchers.IO){
                val json = exception.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
                throw AwesomeApiException(errorResponse.message)
            }
        }
    }
}