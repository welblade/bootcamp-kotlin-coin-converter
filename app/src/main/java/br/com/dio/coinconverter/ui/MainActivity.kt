package br.com.dio.coinconverter.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import br.com.dio.coinconverter.data.model.Coin
import br.com.dio.coinconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindAdapters()
        bindListeners()
    }

    private fun bindListeners() {
        binding.tilValue.editText?.doAfterTextChanged {
            binding.btnConvert.isEnabled = !it.isNullOrEmpty()
        }
        binding.btnConvert.setOnClickListener {

        }
    }

    private fun bindAdapters(){
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Coin.values())
        binding.tvFrom.apply{
            setAdapter(adapter)
            setText(Coin.BRL.name, false)
        }
        binding.tvTo.apply {
            setAdapter(adapter)
            setText(Coin.USD.name, false)
        }
    }
}