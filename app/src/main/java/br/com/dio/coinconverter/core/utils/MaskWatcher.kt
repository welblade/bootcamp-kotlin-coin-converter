package br.com.dio.coinconverter.core.utils

import android.text.Editable
import android.text.TextWatcher
import br.com.dio.coinconverter.core.extensions.formatToValueEditText

class MaskWatcher : TextWatcher {
    private var isRunning = false

    override fun beforeTextChanged(
        charSequence: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {
        if (isRunning) {
            return
        }
        isRunning = true
        val editableLength = editable.length
        val text = editable.toString()
        editable.replace(0, editableLength, text.formatToValueEditText())
        isRunning = false
    }
}