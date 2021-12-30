package br.com.dio.coinconverter.data.di

import android.util.Log
import br.com.dio.coinconverter.core.utils.JsonDataAsset
import br.com.dio.coinconverter.data.datasource.AvailableExchangeDatasource
import br.com.dio.coinconverter.data.datasource.AvailableExchangeDatasourceImpl
import br.com.dio.coinconverter.data.repository.*
import br.com.dio.coinconverter.data.service.AwesomeService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModules {
    private const val HTTP_TAG = "OkHttp"

    fun load(){
        loadKoinModules(
            networkModule()
                    + repositoryModule()
                    + datasourceModule()
        )
    }

    private fun networkModule(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(HTTP_TAG, ": $it")
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<AwesomeService>(get(), get())
            }
        }
    }

    private fun repositoryModule() = module {
        single<CoinRepository> { CoinRepositoryImpl(get()) }
        single<CoinListRepository> { CoinListRepositoryImpl() }
        single<AvailableExchangeRepository> { AvailableExchangeRepositoryImpl(get()) }
    }

    private fun datasourceModule(): Module{
        return module {
            factory { JsonDataAsset(androidContext()) }
            single<AvailableExchangeDatasource> { AvailableExchangeDatasourceImpl(get()) }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit.Builder()
            .baseUrl("https://economia.awesomeapi.com.br/json/")
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(T::class.java)
    }
}