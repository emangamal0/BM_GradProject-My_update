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

    private val _state2 = mutableStateOf(DropDownState())
    val state2: State<DropDownState> = _state2

    private val _state3 = mutableStateOf(DropDownState())
    val state3: State<DropDownState> = _state3

    private val _state4 = mutableStateOf(DropDownState())
    val state4: State<DropDownState> = _state4

    private val _state5 = mutableStateOf(DropDownState())
    val state5: State<DropDownState> = _state5


    private val currencyDao = CurrencyDatabase.getInstance(AppContext.appContext).currencyDao()
    private val currencyRepository: CurrencyRepository = CurrencyRepositoryImpl(currencyDao)

    private val _remoteCurrencies = MutableLiveData<List<CachedCurrency>>()
    val remoteCurrencies: LiveData<List<CachedCurrency>> = _remoteCurrencies

    private val _conversionState = MutableLiveData<Double>()
    val conversionState: LiveData<Double> = _conversionState

    private val _compareState = MutableLiveData<CompareState>(CompareState())
    val compareState: LiveData<CompareState> = _compareState

    var  cachedCurrencies : List<CachedCurrency> = emptyList()

    val myPorotfoilList =currencyRepository.getCachedCurrenciesLiveData()

    init {
        viewModelScope.launch {
            fetchRemoteCurrencies()
        }
    }

    suspend fun fetchRemoteCurrencies() {

            val apiService = RetrofitClient.createService(ApiService::class.java)
            val currencies = apiService.getCurrencies()
            val cachedCurrencies = currencies.map { currency ->
                CachedCurrency(currency.code, currency.desc, currency.flagUrl)
            }
            _remoteCurrencies.postValue(cachedCurrencies)
            //currencyRepository.insertCurrencies(cachedCurrencies)
    }

    private fun fetchCachedCurrencies() {
        cachedCurrencies = currencyRepository.getCachedCurrenciesLiveData().value ?: emptyList()
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
        //try {
        Log.i("CurrencyViewModel" , "from=$from to=$to amount=$amount")
            val apiService = RetrofitClient.createService(ApiService::class.java)
            val conversionResult = apiService.getConversionResult(from, to , amount)
            val result = conversionResult.value
            _conversionState.postValue(result)
            Log.d("eman", result.toString())
//        }catch (e: Exception) {
//            Log.e("CurrencyViewModel", "Error fetching remote data: ${e.message}")
//
//        }
    }

    suspend fun compare(from : String, to: List<String> , amount: Double){
        try {
            Log.i("CompareCurrencyViewModel" , "from=$from to=$to")
            val apiService = RetrofitClient.createService(ApiService::class.java)
            val compareResult = apiService.getCompareResult(from , "${to[0]},${to[1]}" ,amount)

            _compareState.postValue(CompareState(firstCurrency = compareResult[0].value , secondCurrency = compareResult[1].value
            ))
            Log.d("eman", compareResult.toString())
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
            is Event.Compare -> {
                viewModelScope.launch {
                    compare(event.from, event.to, event.amount)
                }
            }

        }
    }

    fun setDropDownState(selectedCurrencyIndex: String= "" , num : Int) {
        when(num){
            1 -> {
                _state.value = DropDownState(selectedCurrencyIndex) //from convert
            }
            2 -> {
                _state2.value = DropDownState(selectedCurrencyIndex) //to convert
            }

            3 ->{
                _state3.value = DropDownState(selectedCurrencyIndex) // from compare
            }

            4 ->{
                _state4.value = DropDownState(selectedCurrencyIndex) // target 1
            }

            5 ->{
            _state5.value = DropDownState(selectedCurrencyIndex) // target 2
        }


        }
    }

}

data class CompareState(val firstCurrency :Double =0.0,val secondCurrency :Double=0.0  )