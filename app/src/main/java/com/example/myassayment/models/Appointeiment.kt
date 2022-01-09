package com.example.myassayment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Appointeiment(
    var appointeimentId:String="",
    var timeSchedule: TimeSchedule?=null,
    var timeBook:String?="",
    var clientId:String?="",
    var client:Client?=null,
    var doctorId:String?="",
    var doctor: Doctor?=null
):Parcelable {
}