package com.example.currencyconverter.data.local.dp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CachedCurrency::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getInstance(context: Context): CurrencyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    "currency_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}