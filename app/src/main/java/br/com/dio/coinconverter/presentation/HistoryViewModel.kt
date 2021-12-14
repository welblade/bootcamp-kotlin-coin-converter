package br.com.dio.coinconverter.presentation

import androidx.lifecycle.*
import br.com.dio.coinconverter.data.model.ExchangeResponseValue
import br.com.dio.coinconverter.domain.ListExchangeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val useCase: ListExchangeUseCase
): ViewModel(), LifecycleObserver {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun getExchangeHistory(coin: String) {
        viewModelScope.launch {
            useCase()
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
    sealed class State(){
        object Loading: State()
        object Empty: State()
        data class Success(val list: List<ExchangeResponseValue>): State()
        data class Error(val error: Throwable): State()
    }
}