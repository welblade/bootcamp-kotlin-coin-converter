package br.com.dio.coinconverter.data.datasource

import br.com.dio.coinconverter.core.utils.JsonDataAsset
import br.com.dio.coinconverter.data.model.AvailableExchange
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AvailableExchangeDatasourceImpl(
    asset: JsonDataAsset,
) : AvailableExchangeDatasource {
    private val availableExchanges: List<AvailableExchange>

    init {
        val jsonFileString = asset.getJsonDataFromAsset("available_exchanges.json")
        val gson = Gson()
        val listAvailableExchangeType = object : TypeToken<List<AvailableExchange>>() {}.type
        availableExchanges =  gson.fromJson(jsonFileString, listAvailableExchangeType)
    }

    override suspend fun getExchangesAvailableTo(coin: String): List<AvailableExchange> {
        return availableExchanges.filter { it.abbr.startsWith(coin) }
    }
}