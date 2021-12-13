package br.com.dio.coinconverter.domain.di

import br.com.dio.coinconverter.domain.GetExchangeValueUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModules {
    fun load(){
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory { GetExchangeValueUseCase(get()) }
        }
    }
}