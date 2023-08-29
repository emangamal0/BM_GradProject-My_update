package com.example.currencyconverter.data.repo

import androidx.lifecycle.LiveData
import com.example.currencyconverter.data.local.dp.CachedCurrency
import com.example.currencyconverter.data.local.dp.CurrencyDao
import com.example.currencyconverter.domain.CurrencyRepository

class CurrencyRepositoryImpl(private val currencyDao: CurrencyDao) : CurrencyRepository {
    override fun getCachedCurrenciesLiveData(): LiveData<List<CachedCurrency>> {
            return currencyDao.getCurrenciesLiveData()
    }

    override suspend fun insertCurrencies(currencies: List<CachedCurrency>) {
        currencyDao.insertCurrencies(currencies)
    }

    override suspend fun updateCurrency(currency: CachedCurrency) {
        currencyDao.flagItemAsFavourite(currency)
    }
}