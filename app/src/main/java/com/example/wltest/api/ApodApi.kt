package com.example.wltest.api

import com.example.wltest.data.model.ApodData
import retrofit2.http.GET
import retrofit2.http.Query
interface ApodApi {
    @GET("/planetary/apod")
    suspend fun getAstronomyPicture(@Query("api_key") apiKey : String) : ApodData
}