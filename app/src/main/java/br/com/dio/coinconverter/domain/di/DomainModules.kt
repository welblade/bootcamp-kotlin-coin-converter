package br.com.dio.coinconverter.domain.di

import br.com.dio.coinconverter.domain.GetExchangeVaueUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModules {
    fun load(){
        loadKoinModules(useCaseModules())
    }

    private fun useCaseModules(): Module {
        return module {
            factory { GetExchangeVaueUseCase(get()) }
        }
    }
}