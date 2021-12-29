package br.com.dio.coinconverter.core.extensions

fun String.formatToValueEditText():String {
    return this
        .replace("^[0,]*".toRegex(), "")
        .replace(",", "")
        .padStart(3, '0').let{ s ->
            "${s.take(s.length-2)},${s.takeLast(2)}"
        }
}