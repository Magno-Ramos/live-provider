package com.app.liveprovider

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SampleViewModel : ViewModel() {

    val data by liveProvider(DataSource.liveData)
}