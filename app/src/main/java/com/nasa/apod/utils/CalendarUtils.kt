package com.nasa.apod.utils

import java.text.SimpleDateFormat
import java.util.*

object CalendarUtils {

    private const val DATE_FORMAT = "yyyy-MM-dd"

    fun getDateString(cal: Calendar): String {
        return SimpleDateFormat(DATE_FORMAT, Locale.US).format(cal.time)
    }
}