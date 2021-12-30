package br.com.dio.coinconverter.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
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
        setSupportActionBar(binding.toolbar)
        setFragment()
        setRecyclerView()
        bindingObservers()
        bindListeners()
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
                    exchangeRateAdapter.apply {
                        submitList(it.response.values.toList())
                    }
                }
            }
        }
    }

    private fun bindListeners() {
        buttonListFragment.doAfterCoinChanged {
            binding.tvCoinAbbr.text = it.name
            val valueToConvert = binding.tilValue.text.replace(",", ".").toDouble()
            if(valueToConvert > 0.0) setButtonConvertVisible(true)
        }

        binding.tilValue.editText!!.addTextChangedListener(MaskWatcher())
        binding.tilValue.editText!!.doAfterTextChanged {
            val valueToConvert = binding.tilValue.text.replace(",", ".").toDouble()
            if(valueToConvert > 0.0) setButtonConvertVisible(true)
        }
        binding.tilValue.editText!!.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                textView.hideSoftKeyboard()
                convertValue()
                this.setButtonConvertVisible(false)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.btnConvert.setOnClickListener {
            it.hideSoftKeyboard()
            convertValue()
            setButtonConvertVisible(false)
        }
        binding.btnEdit.setOnClickListener {
            val textSize = binding.tilValue.editText!!.text.length
            binding.tilValue.editText!!.apply {
                requestFocus()
                setSelection(0, textSize)
                showSoftKeyboard()
            }
        }
    }

    private fun setButtonConvertVisible (visible: Boolean) {
        binding.btnEdit.visibility = if(!visible) View.VISIBLE else View.GONE
        binding.btnConvert.visibility = if(visible) View.VISIBLE else View.GONE
    }

    private fun convertValue() {
        val coinToConvert = buttonListFragment.getSelectedButton()
        if (lastCoinUsed == null || lastCoinUsed != coinToConvert) {
            mainViewModel.getExchangeValues(coinToConvert.name)
        }
        val valueToConvert = binding.tilValue.text.replace(",", ".").toDouble()
        exchangeRateAdapter.convertExchange(valueToConvert)
    }
}