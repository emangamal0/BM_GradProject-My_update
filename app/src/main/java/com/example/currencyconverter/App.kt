package com.example.currencyconverter

import android.app.Application
import android.content.Context

class App :Application() {

    override fun onCreate() {
        super.onCreate()
        AppContext.initContext(this)
    }
}


object AppContext{
    lateinit var appContext : Context


    fun initContext (context :Context){
        appContext = context
    }
}