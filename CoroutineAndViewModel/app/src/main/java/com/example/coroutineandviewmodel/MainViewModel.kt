package com.example.coroutineandviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel:ViewModel() {
    private val _workingInfo= MutableLiveData<String>()
    val workingInfo: LiveData<String>
        get()=_workingInfo
    private val _currentState=MutableLiveData(WorkState.IDLE)
    val currentState: LiveData<WorkState>
        get()=_currentState
    //引用当前的任务
    private var job:Job?=null
    //执行数据处理任务
    fun doWork(){
        job=viewModelScope.launch {
            for(i in 0..100 step 10){
                ensureActive()
                _workingInfo.value=process(i)
            }
        }
    }
    private suspend fun process(percent:Int):String{
        return withContext(Dispatchers.IO){
            _currentState.postValue(WorkState.WORKING)
            delay(1000)
            var info="协程使用线程${Thread.currentThread().id}工作：$percent%"
            if(percent==100){
                info="工作已完成"
                _currentState.postValue(WorkState.IDLE)
                job=null
            }
            return@withContext info
        }
    }
    fun cancel(){
        job?.cancel()
        _currentState.value=WorkState.CANCELLED
        job=null
    }
}