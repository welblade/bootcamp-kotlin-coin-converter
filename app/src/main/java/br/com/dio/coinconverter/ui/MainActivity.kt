package br.com.dio.coinconverter.ui

import android.graphics.Rect
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.text.method.TransformationMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.coinconverter.R
import br.com.dio.coinconverter.core.extensions.*
import br.com.dio.coinconverter.core.utils.MaskWatcher
import br.com.dio.coinconverter.data.model.Coin
import br.com.dio.coinconverter.databinding.ActivityMainBinding
import br.com.dio.coinconverter.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModel<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val progress by lazy { createProgressDialog() }
    private val buttonListFragment by lazy { CoinButtonListFragment() }
    private val exchangeRateAdapter by lazy { ExchangeRateItemAdapter() }
    private var lastCoinUsed: Coin? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindListeners()
        bindingObservers()
        setFragment()
        setSupportActionBar(binding.toolbar)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.rvExchangeRateList.layoutManager = LinearLayoutManager(this)
        binding.rvExchangeRateList.adapter = exchangeRateAdapter
    }

    private fun setFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(binding.fcvCoinButtonList.id, buttonListFragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_history) {
            //startActivity(Intent(this, HistoryActivity::class.java))
            Log.e("ok", "ok")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindingObservers() {
        mainViewModel.state.observe(this) {
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
                    exchangeRateAdapter.apply{
                        submitList(it.response.values.toList())
                    }
                }
            }
        }
    }

    private fun bindListeners() {
        buttonListFragment.doAfterCoinChanged {
            binding.tvCoinAbbr.text = it.name
        }

        binding.tilValue.editText!!.addTextChangedListener(MaskWatcher())

        binding.btnConvert.setOnClickListener {
            binding.tilValue.isEnabled = false
            it.hideSoftKeyboard()
            convertValue()
            toggleButtonVisibility()
        }
        binding.btnEdit.setOnClickListener {
            binding.tilValue.isEnabled = true
            toggleButtonVisibility()
        }
    }
    private fun toggleButtonVisibility(){
        val visibility = binding.btnEdit.visibility
        binding.btnEdit.visibility = binding.btnConvert.visibility
        binding.btnConvert.visibility = visibility
    }

    private fun convertValue(){
        val coinToConvert = buttonListFragment.getSelectedButton()
        if (lastCoinUsed == null || lastCoinUsed != coinToConvert){
            mainViewModel.getExchangeValues(coinToConvert.name)
        }
        val valueToConvert = binding.tilValue.text.replace(",", ".").toDouble()
        exchangeRateAdapter.convertExchange(valueToConvert)
    }
}