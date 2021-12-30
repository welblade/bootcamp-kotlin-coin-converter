package br.com.dio.coinconverter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.coinconverter.data.model.ExchangeResponse
import br.com.dio.coinconverter.domain.GetAvailableExchangesUseCase
import br.com.dio.coinconverter.domain.GetExchangeValueUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val getExchangeUseCase: GetExchangeValueUseCase,
    private val getAvailableExchangesUseCase: GetAvailableExchangesUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private fun getExchangeValue(coin: String) {
        viewModelScope.launch {
            getExchangeUseCase(coin)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Success(it)
                }
        }
    }

    fun getExchangeValues(coin: String) {
        viewModelScope.launch {
            getAvailableExchangesUseCase(coin)
                .flowOn(Dispatchers.Main)
                .catch {
                    _state.value = State.Error(it)
                }
                .collect { list ->
                    val coins = list.joinToString(",") { it.abbr }
                    getExchangeValue(coins)
                }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val response: ExchangeResponse) : State()
        data class Error(val error: Throwable) : State()
    }
}