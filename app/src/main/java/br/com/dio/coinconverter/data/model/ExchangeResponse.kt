package br.com.dio.coinconverter.data.model

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