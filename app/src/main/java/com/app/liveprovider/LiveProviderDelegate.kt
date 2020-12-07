package com.app.liveprovider

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@InternalCoroutinesApi
class LiveProviderDelegate<T>(viewModel: ViewModel, liveData: LiveData<T>) :
    ReadOnlyProperty<ViewModel, T?> {
    private var value: T? = null

    init {
        val scope = CoroutineScope(Dispatchers.Main)
        val observer: Observer<T> = Observer { value = it }

        viewModel.viewModelScope.launch {
            suspendCoroutine<Nothing> {
                scope.launch { liveData.observeForever(observer) }
            }
        }.invokeOnCompletion(onCancelling = true) {
            scope.launch { liveData.removeObserver(observer) }
        }
    }

    override fun getValue(thisRef: ViewModel, property: KProperty<*>): T? = value
}

@InternalCoroutinesApi
fun <T> ViewModel.liveProvider(liveData: LiveData<T>) = LiveProviderDelegate(this, liveData)