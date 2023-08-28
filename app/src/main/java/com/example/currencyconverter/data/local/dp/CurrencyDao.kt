package com.example.currencyconverter.data.local.dp


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<CachedCurrency>)

    @Query("SELECT * FROM currencies")
    fun getCurrenciesLiveData(): LiveData<List<CachedCurrency>>

    @Query("SELECT * FROM currencies")
    suspend fun getCurrencies(): List<CachedCurrency>

    @Update
    suspend fun flagItemAsFavourite(item: CachedCurrency)
}