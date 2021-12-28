package br.com.dio.coinconverter.core.utils

import android.content.Context
import java.io.IOException

class JsonDataAsset(private val context: Context) {

    fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}
