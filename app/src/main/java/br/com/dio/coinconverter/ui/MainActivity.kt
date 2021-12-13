package br.com.dio.coinconverter.ui

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import br.com.dio.coinconverter.core.extensions.*
import br.com.dio.coinconverter.data.model.Coin
import br.com.dio.coinconverter.databinding.ActivityMainBinding
import br.com.dio.coinconverter.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val progress by lazy { createProgressDialog() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindAdapters()
        bindListeners()
        bindingObservers()
    }

    private fun bindingObservers() {
        viewModel.state.observe(this) {
            when (it) {
                MainViewModel.State.Loading -> {
                    progress.show()
                }
                is MainViewModel.State.Error -> {
                    progress.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                is MainViewModel.State.Success -> {
                    progress.dismiss()
                    val coinToConverterName = binding.tilTo.text
                    val coinToConverter = Coin.values().find {
                            coin -> coin.name == coinToConverterName
                    } ?: Coin.BRL
                    val valueToConverter = binding.tilValue.text.toDouble()
                    val result = valueToConverter * it.exchange.bid
                    binding.tvResult.text = result.formatCurrency(coinToConverter.locale)
                    Log.e("Converting", "onCreate: ${it.exchange}")
                }
            }
        }
    }

    private fun bindListeners() {
        binding.tilValue.editText?.doAfterTextChanged {
            binding.btnConvert.isEnabled = !it.isNullOrEmpty()
        }
        binding.btnConvert.setOnClickListener {
            it.hideSoftKeyboard()
            val currency = "${binding.tilFrom.text}-${binding.tilTo.text}"
            viewModel.getExchangeValue(currency)
            Log.e("TAG", "bindListeners: " + binding.tilValue.text)
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