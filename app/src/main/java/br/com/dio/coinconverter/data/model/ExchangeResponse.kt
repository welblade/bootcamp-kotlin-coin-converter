package br.com.dio.coinconverter.data.model

import androidx.room.Entity

typealias ExchangeResponse = HashMap<String, ExchangeResponseValue>

data class ExchangeResponseValue (
    val code: String,
    val codein: String,
    val name: String,
    val high: String,
    val low: String,
    val varBid: String,
    val pctChange: String,
    val bid: Double,
    val ask: String,
    val timestamp: String,
    val createDate: String
)

@Entity(tableName = "tb_exchange")
data class ExchangeValueEntity(
    val code: String,
    val codein: String,
    val name: String,
    val high: String,
    val low: String,
    val varBid: String,
    val pctChange: String,
    val bid: Double,
    val ask: String,
    val timestamp: String,
    val createDate: String
) {
    companion object {
        fun from(value: ExchangeResponseValue): ExchangeValueEntity {
            return ExchangeValueEntity(value.code,
                value.codein,
                value.name,
                value.high,
                value.low,
                value.varBid,
                value.pctChange,
                value.bid,
                value.ask,
                value.timestamp,
                value.createDate)
        }
    }

    fun toExchangeResponseValue(): ExchangeResponseValue {
        return ExchangeResponseValue(code,
            codein,
            name,
            high,
            low,
            varBid,
            pctChange,
            bid,
            ask,
            timestamp,
            createDate)
    }
}