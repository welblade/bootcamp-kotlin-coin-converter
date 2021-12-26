package br.com.dio.coinconverter.presentation

import androidx.lifecycle.*
import br.com.dio.coinconverter.data.model.Coin
import br.com.dio.coinconverter.domain.ListCoinUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CoinButtonListViewModel(
    private val listCoinUseCase: ListCoinUseCase
):ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun getCoinList() {
        viewModelScope.launch {
            listCoinUseCase()
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
    sealed class State {
        object Loading: State()
        object Empty: State()
        data class Success(val coins: List<Coin>): State()
        data class Error(val error: Throwable): State()
    }
}