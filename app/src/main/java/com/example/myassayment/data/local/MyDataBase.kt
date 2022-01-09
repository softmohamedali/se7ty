package com.example.myassayment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myassayment.data.local.Entitys.PreviousBooking

@Database(
    entities = arrayOf(PreviousBooking::class),
    version = 2,
    exportSchema = false
)
@TypeConverters(MyTypeConverter::class)
abstract class MyDataBase:RoomDatabase() {
    abstract fun getDao():Dao
}