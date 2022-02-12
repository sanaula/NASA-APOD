package com.example.wltest.data.di

import android.content.Context
import androidx.room.Room
import com.example.wltest.Constant.APOD_DATABASE
import com.example.wltest.data.local.ApodDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ApodDatabase {
        return Room.databaseBuilder(context,
            ApodDatabase::class.java, APOD_DATABASE).build()
    }

}