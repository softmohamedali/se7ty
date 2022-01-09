package com.example.myassayment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookTest(
    var id:String?="",
    var userId:String?="",
    var branchName:String?="",
    var location:String?="",
    var branchId:String?="",
    var place:String?="",
    var forWho:String?="",
    var patientName:String?="",
    var patientPhone:String?="",
    var city:String?="",
    var area:String?="",
    var patientAddres:String?="",
    var lapTests:List<LapTests>?= emptyList()
):Parcelable {

}