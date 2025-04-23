package com.example.viewmodeltest

import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory(val number : Int): ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(number) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}