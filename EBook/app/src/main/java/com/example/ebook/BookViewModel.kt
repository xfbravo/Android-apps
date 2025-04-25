package com.example.ebook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookViewModel: ViewModel() {
    private val _fontSize= MutableLiveData(20f)
    val fontSize: MutableLiveData<Float>
        get() = _fontSize
    fun updateFontSize(newSize: Float) {
        _fontSize.value = newSize
    }

    private val _isNightMode= MutableLiveData(false)
    val isNightMode: MutableLiveData<Boolean>
        get() = _isNightMode
    fun toggleNightMode(){
        _isNightMode.value = _isNightMode.value?.not()
    }
}