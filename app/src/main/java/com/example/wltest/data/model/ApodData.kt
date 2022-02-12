package com.example.wltest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wltest.Constant.APOD_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = APOD_TABLE)
data class ApodData(
    @PrimaryKey(autoGenerate = false)
    val date: String,
    val explanation: String,
    val hdurl: String,
    val title: String,
    val url: String
)
