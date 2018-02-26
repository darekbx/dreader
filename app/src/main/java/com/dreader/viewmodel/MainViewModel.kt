package com.dreader.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.dreader.api.Service
import com.dreader.extensions.threadToAndroid
import com.dreader.model.Item
import io.reactivex.Observable

class MainViewModel(application: Application): AndroidViewModel(application) {

    val itemsList = MutableLiveData<List<Item>>()
    val errorObserver =  MutableLiveData<String>()

    fun fetch(offset: Int = 0) {
        Observable
                .fromCallable { service.getItems(offset) }
                .map { it.execute() }
                .map { it.body() ?: throw IllegalStateException() }
                .map { it.items }
                .map { it.toList() }
                .flatMapIterable { list -> list }
                .filter{ it.hasValidType() }
                .toList()
                .threadToAndroid()
                .subscribe(
                        { itemsList.postValue(it) },
                        { e -> postError(e) })
    }

    private fun postError(e: Throwable) {
        e.printStackTrace()
        errorObserver.postValue(e.message)
    }

    private val service by lazy { Service().apply { setup() }.endpoints }
}