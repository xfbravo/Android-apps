package com.example.rolldice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewModelRollDice:ViewModel() {
    val dieValue= MutableLiveData<Pair<Int,Int>>()

    fun rollTheDice(){
        for(dieIndex in 0 until 5){
            viewModelScope.launch{
                delay(dieIndex*10L)
                for(i in 1..20){
                    val number=getDieValue()
                    dieValue.value=Pair(dieIndex,number)
                    delay(100)
                }
            }
        }
    }
    private fun getDieValue(): Int {
        return (1..6).random()
    }
}