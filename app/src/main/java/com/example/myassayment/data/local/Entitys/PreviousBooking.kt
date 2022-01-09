package com.example.myassayment.data.local.Entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myassayment.models.Appointeiment
import com.example.myassayment.utils.Constants

@Entity(tableName = Constants.TABLE_NAME_PREVIOUSBOOKING)
data class PreviousBooking(
    var appointement: Appointeiment? = Appointeiment(),
    var status:String?=""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}