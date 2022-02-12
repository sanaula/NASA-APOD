package com.example.wltest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paging3demo.data.local.dao.ApodDao
import com.example.wltest.data.model.ApodData

@Database(entities = [ApodData::class], version = 1)
abstract class ApodDatabase : RoomDatabase() {

    abstract fun getApodDao(): ApodDao

}