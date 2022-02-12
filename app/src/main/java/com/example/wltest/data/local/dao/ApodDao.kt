package com.example.paging3demo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wltest.data.model.ApodData

@Dao
interface ApodDao {

    @Query("SELECT * FROM apod_table")
    suspend fun getPictureOfDay(): ApodData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPictureOfDay(data: ApodData)

    @Query("DELETE FROM apod_table")
    suspend fun deleteAllPictures()

}