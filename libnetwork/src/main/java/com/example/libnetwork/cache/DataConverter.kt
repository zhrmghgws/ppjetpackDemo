package com.example.libnetwork.cache

import androidx.room.TypeConverter
import java.util.*

object DataConverter {
    @TypeConverter
    fun data2Long(date: Date):Long{
        return date.time
    }
    @TypeConverter
    fun long2Date(date:Long):Date{
        return Date(date)
    }
}