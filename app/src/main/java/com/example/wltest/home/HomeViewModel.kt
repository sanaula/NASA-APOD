package com.example.wltest.home

import android.content.Context
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wltest.Constant.IS_FIRST_TIME
import com.example.wltest.Constant.LAST_UPDATE_TIME
import com.example.wltest.CustomDateUtils
import com.example.wltest.R
import com.example.wltest.data.local.LocalStorageManager
import com.example.wltest.data.model.ApodData
import com.example.wltest.data.repository.Repository
import com.example.wltest.extensions.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private var appContext: Context?, // warning handled in oncleared
    private val repository: Repository
) : ViewModel() {

    private val _pictureLiveData = MutableLiveData<ApodData?>()
    val pictureLiveData: LiveData<ApodData?> = _pictureLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _fullPictureLiveData = MutableLiveData<ApodData>()
    val fullPictureLiveData: LiveData<ApodData> = _fullPictureLiveData

    init {
        viewModelScope.launch {
            val storedTime = LocalStorageManager.getStoredTime(LAST_UPDATE_TIME)
            if (storedTime != 0L && dateNotToday(storedTime)) {
                LocalStorageManager.setFirstTime(IS_FIRST_TIME, true)
            }

            if (appContext?.applicationContext?.isNetworkAvailable() == true) {
                try {
                    if (LocalStorageManager.isFirstTime(IS_FIRST_TIME)) {
                        val picturesFromRemote = repository.getPicturesFromRemote()
                        withContext(Dispatchers.Main.immediate) {
                            _pictureLiveData.value = picturesFromRemote
                            LocalStorageManager.storeTime(
                                LAST_UPDATE_TIME,
                                System.currentTimeMillis()
                            )
                        }
                        LocalStorageManager.setFirstTime(IS_FIRST_TIME, false)
                    } else {
                        _fullPictureLiveData.value = repository.getPicturesFromDB()
                    }

                } catch (e: Exception) {
                    setDataFromLocal()
                }
            } else {
                setDataFromLocal()
            }
        }
    }

    private suspend fun setDataFromLocal() = withContext(Dispatchers.Main.immediate) {
        val picturesFromRemote = repository.getPicturesFromDB()
        picturesFromRemote?.let {
            val time = CustomDateUtils.getTime(picturesFromRemote.date)
            if (time != 0L && dateNotToday(time)) {
                _errorLiveData.value = appContext?.getString(R.string.alert_info)
            }
        }
        _pictureLiveData.value = picturesFromRemote
    }

    private fun dateNotToday(storedTime: Long) = DateUtils.isToday(storedTime).not()

    override fun onCleared() {
        super.onCleared()
        appContext = null
    }
}

