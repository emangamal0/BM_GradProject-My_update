package com.example.currencyconverter.domain

import androidx.lifecycle.LiveData
import com.example.currencyconverter.data.local.dp.CachedCurrency

interface CurrencyRepository {
    fun getCachedCurrenciesLiveData(): LiveData<List<CachedCurrency>>
    suspend fun insertCurrencies(currencies: List<CachedCurrency>)
    suspend fun updateCurrency(currency:CachedCurrency)
}
