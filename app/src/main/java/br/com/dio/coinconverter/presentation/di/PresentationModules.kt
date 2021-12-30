package br.com.dio.coinconverter.presentation.di

import br.com.dio.coinconverter.presentation.CoinButtonListViewModel
import br.com.dio.coinconverter.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModules {
    fun load() {
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule(): Module {
        return module {
            viewModel { MainViewModel(get(), get()) }
            viewModel { CoinButtonListViewModel(get()) }
        }
    }
}