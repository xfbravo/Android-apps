package com.example.fragmentwithviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BlankViewModel : ViewModel() {
    var _counter:MutableLiveData <Int> = MutableLiveData(0)
    val counter:LiveData<Int>
        get() = _counter
    fun changeCounter(number:Int){
        _counter.postValue(number)
    }
}