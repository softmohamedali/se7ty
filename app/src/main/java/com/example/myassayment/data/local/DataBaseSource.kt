package com.example.myassayment.data.local

import com.example.myassayment.data.local.Entitys.PreviousBooking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseSource @Inject constructor(
    private var myDao: Dao
){
    suspend fun insertPreviousBooking(preoBooking: PreviousBooking)=
        myDao.insertPreviousBooking(preoBooking)

    fun getAllPreBooking()=myDao.getAllPreBooking()
}