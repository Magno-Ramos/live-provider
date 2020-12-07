package com.app.liveprovider

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

object DataSource {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    val liveData = MutableLiveData<Int>()

    init {
        scope.launch {
            increment()
        }
    }

    private suspend fun increment() {
        Log.d("APP_MAIN","increment value")
        liveData.postValue((liveData.value ?: 0) + 1)
        delay(1000)
        increment()
    }
}