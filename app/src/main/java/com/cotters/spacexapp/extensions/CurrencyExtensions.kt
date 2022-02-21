package com.cotters.spacexapp.extensions

import java.text.NumberFormat
import java.util.*

fun Number.toDollarString(): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance()
    currencyFormatter.maximumFractionDigits = 0
    currencyFormatter.currency = Currency.getInstance("USD")
    return currencyFormatter.format(this)
}
