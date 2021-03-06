package com.example.myassayment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeSchedule (
    var id:String?="",
    var fromTime:String?="",
    var toTime:String?="",
    var date:String?="",
    var user:Client?=Client()
):Parcelable{

    fun getAllDate():String="day:${this.date},time:${fromTime}"
}