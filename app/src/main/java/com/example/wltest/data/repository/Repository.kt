package com.example.wltest.data.repository

import com.example.wltest.Constant.API_KEY
import com.example.wltest.api.ApodApi
import com.example.wltest.data.local.ApodDatabase
import com.example.wltest.data.model.ApodData
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class Repository @Inject constructor(
    private val api: ApodApi,
    private val apodDatabase: ApodDatabase
) {

    suspend fun getPicturesFromRemote(): ApodData? = withContext(Dispatchers.IO) {
        val apodData = api.getAstronomyPicture(API_KEY)
        deleteAllPicturesFromDB()
        storePictureIntoDB(apodData) //could store api_key in locally in gradle.properties
        apodData
    }

    suspend fun getPicturesFromDB(): ApodData? {
        return apodDatabase.getApodDao().getPictureOfDay()
    }

    private suspend fun storePictureIntoDB(apodData: ApodData){
        apodDatabase.getApodDao().addPictureOfDay(apodData)
    }

    suspend fun deleteAllPicturesFromDB(){
        apodDatabase.getApodDao().deleteAllPictures()
    }

}