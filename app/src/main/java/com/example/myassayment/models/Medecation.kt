package com.example.myassayment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Medecation(
    var id:String?="",
    var userid:String?="",
    var medicationName:String?="",
    var notes:String?="",
    var chronicDrug:String?=""
):Parcelable {
}