package com.example.currencyconverter.domain


data class Currency(val code: String, val flagUrl: String, val desc: String)

data class ConversionRate(val from: String, val to: String, val rate: Double)

data class ConversionResult(val from: String, val to: String , val value: Double)

data class CompareResult(val from: String, val to: String , val value: Double)