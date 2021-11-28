package com.example.myassayment.models

data class TimeSchedule (
    var id:String?="",
    var fromTime:String?="",
    var toTime:String?="",
    var date:String?="",
    var user:Client?=Client()
){
}