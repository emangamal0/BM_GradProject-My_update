package com.example.currencyconverter.ui


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.AppContext
import com.example.currencyconverter.data.local.dp.CachedCurrency
import com.example.currencyconverter.data.local.dp.CurrencyDatabase
import com.example.currencyconverter.data.remote.api.ApiService
import com.example.currencyconverter.data.remote.api.RetrofitClient
import com.example.currencyconverter.data.repo.CurrencyRepositoryImpl
import com.example.currencyconverter.domain.CurrencyRepository
import com.example.currencyconverter.persentation.DropDownState
import com.example.currencyconverter.persentation.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel() : ViewModel() {
    private val _state = mutableStateOf(DropDownState())
    val state: State<DropDownState> = _state


    private val currencyDao = CurrencyDatabase.getInstance(AppContext.appContext).currencyDao()
    private val currencyRepository: CurrencyRepository = CurrencyRepositoryImpl(currencyDao)
    private val _remoteCurrencies = MutableLiveData<List<CachedCurrency>>()
    val remoteCurrencies: LiveData<List<CachedCurrency>> = _remoteCurrencies
    private val _conversionState = MutableLiveData<Double>()
    val conversionState: LiveData<Double> = _conversionState
    lateinit var  cachedCurrencies : List<CachedCurrency>
    init {
        viewModelScope.launch {
            fetchRemoteCurrencies()
        }

    }

    suspend fun fetchRemoteCurrencies() {
        try {
            val apiService = RetrofitClient.createService(ApiService::class.java)
            val currencies = apiService.getCurrencies()
            val cachedCurrencies = currencies.map { currency ->
                CachedCurrency(currency.code, currency.desc, currency.flagUrl)
            }
            _remoteCurrencies.postValue(cachedCurrencies)
            currencyRepository.insertCurrencies(cachedCurrencies)

            Log.d("CurrencyViewModel", "Remote Data: $cachedCurrencies")
        } catch (e: Exception) {
            Log.e("CurrencyViewModel", "Error fetching remote data: ${e.message}")


            fetchCachedCurrencies()
        }
    }

    private fun fetchCachedCurrencies() {
        cachedCurrencies = currencyRepository.getCachedCurrenciesLiveData().value!!
        _remoteCurrencies.postValue(cachedCurrencies)


        Log.d("CurrencyViewModel", "Cached Data: $cachedCurrencies")
    }

    fun saveItemInDB(item: CachedCurrency, b: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.updateCurrency(item.copy(isFavourite = b))
            //fetchCachedCurrencies()
        }

    }

   suspend fun convert(from : String, to: String, amount: Double){
        try {
            val apiService = RetrofitClient.createService(ApiService::class.java)
            val conversionResult = apiService.getConversionResult(from, to , amount)
            val result = conversionResult.value
            _conversionState.postValue(result)
            Log.d("eman", result.toString())

        }catch (e: Exception) {
            Log.e("CurrencyViewModel", "Error fetching remote data: ${e.message}")

        }
    }

    fun onEvent(event: Event){
        when(event){
            is Event.Convert -> {
                viewModelScope.launch {
                    convert(event.from, event.to, event.amount)
                }

            }
        }
    }

    fun setDropDownState(selectedCurrencyIndex: String= "") {
        _state.value = DropDownState(selectedCurrencyIndex)
    }
}