package com.cotters.spacexapp.extensions

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun LocalDateTime.dateString(): String {
    return format(DateTimeFormatter.ISO_LOCAL_DATE).toString()
}

fun LocalDateTime.timeString(): String {
    return format(DateTimeFormatter.ISO_LOCAL_TIME).toString()
}

fun Long.toLocalDate(): LocalDateTime {
    return LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC)
}
