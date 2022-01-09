package com.example.myassayment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myassayment.data.local.Entitys.PreviousBooking
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert
    suspend fun insertPreviousBooking(preBookin:PreviousBooking)

    @Query("SELECT * FROM privousbooking")
    fun getAllPreBooking():Flow<List<PreviousBooking>>
}