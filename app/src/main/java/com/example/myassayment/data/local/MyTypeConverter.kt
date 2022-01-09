package com.example.myassayment.data.local

import androidx.room.TypeConverter
import com.example.myassayment.data.local.Entitys.PreviousBooking
import com.example.myassayment.models.Appointeiment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyTypeConverter {
    val json=Gson()

    @TypeConverter
    fun preBookinTOString(prebooking:Appointeiment):String
    {
        return json.toJson(prebooking)
    }

    @TypeConverter
    fun stringTOPreBooking(string:String):Appointeiment
    {
        return json.fromJson(string,object : TypeToken<Appointeiment>(){}.type)
    }

}