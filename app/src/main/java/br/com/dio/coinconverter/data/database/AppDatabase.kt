package br.com.dio.coinconverter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.dio.coinconverter.data.dao.ExchangeDao
import br.com.dio.coinconverter.data.model.ExchangeValueEntity

@Database(entities = [ExchangeValueEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exchangeDao(): ExchangeDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "awesome_database"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}