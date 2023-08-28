package com.example.currencyconverter.data.local.dp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CachedCurrency(
    @PrimaryKey val code: String,
    val desc: String,
    val flagUrl: String,
    val isFavourite :Boolean = false
)
