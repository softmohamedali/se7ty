package com.example.myassayment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LapTests(
    var id:String?="",
    var name:String?=""
):Parcelable {
}