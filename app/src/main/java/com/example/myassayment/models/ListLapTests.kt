package com.example.myassayment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListLapTests (
    var list: MutableList<LapTests>?=null
        ):Parcelable{
}