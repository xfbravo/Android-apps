package com.example.viewmodeltest
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

fun log(message: String) {
    Log.d("MyViewModel", message)
}
class MyViewModel(number:Int): ViewModel() {
    init{
        log("MyViewModel created")
    }
    override fun onCleared() {
        super.onCleared()
        log("MyViewModel destroyed")
    }
    val score:MutableLiveData<Int> = MutableLiveData(number)
}