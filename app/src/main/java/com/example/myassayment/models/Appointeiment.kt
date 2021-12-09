package com.example.myassayment.models

data class Appointeiment(
    var timeSchedule: TimeSchedule?=TimeSchedule(),
    var timeBook:String?="",
    var clientId:String?=""
) {
}