package com.example.currencyconverter.persentation

sealed class Event{
    data class Convert(val from : String, val to: String, val amount: Double): Event()

    data class Compare(val from : String, val to: List<String>, val amount: Double): Event()

}
