package com.example.myassayment.di

import android.content.Context
import androidx.room.Room
import com.example.myassayment.data.local.MyDataBase
import com.example.myassayment.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context)=
        Room.databaseBuilder(
            context,
            MyDataBase::class.java,
            Constants.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideDao(myDataBase: MyDataBase)=
        myDataBase.getDao()
}