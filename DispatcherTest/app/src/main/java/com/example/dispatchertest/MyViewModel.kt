package com.example.dispatchertest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class MyViewModel:ViewModel() {
    fun useCoroutine(){
        viewModelScope.launch(Dispatchers.IO){
            showLog("在ViewModel中使用协程： ${Thread.currentThread().name}")
        }

    }
}