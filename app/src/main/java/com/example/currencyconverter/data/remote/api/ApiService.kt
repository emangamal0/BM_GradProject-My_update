package com.example.currencyconverter.data.remote.api

import com.example.currencyconverter.domain.ConversionRate
import com.example.currencyconverter.domain.ConversionResult
import com.example.currencyconverter.domain.Currency
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("currencies")
    suspend fun getCurrencies(): List<Currency>

    @GET("rates")
    suspend fun getConversionRate(@Query("from") base: String, @Query("to") target: String): ConversionRate

    @GET("convert")
    suspend fun getConversionResult(@Query("from") base: String, @Query("to") target: String, @Query("value") amount: Double): ConversionResult

    @GET("compare")
    suspend fun getCompareResult(@Query("from") base: String, @Query("to") target: String, @Query("value") amount: Double): ConversionResult

}