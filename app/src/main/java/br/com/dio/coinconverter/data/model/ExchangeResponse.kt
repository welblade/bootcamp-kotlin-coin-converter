package br.com.dio.coinconverter.data.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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
    @SerializedName("create_date")
    val createDate: String
)

@Entity(tableName = "tb_exchange")
data class ExchangeValueEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
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
            return ExchangeValueEntity(
                id = null,
                code = value.code,
                codein = value.codein,
                name = value.name,
                high = value.high,
                low = value.low,
                varBid = value.varBid,
                pctChange = value.pctChange,
                bid = value.bid,
                ask = value.ask,
                timestamp = value.timestamp,
                createDate = value.createDate)
        }
    }

    fun toExchangeResponseValue(): ExchangeResponseValue {
        return ExchangeResponseValue(
            code,
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