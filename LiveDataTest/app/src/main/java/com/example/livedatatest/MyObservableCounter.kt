package com.example.livedatatest

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Timer
import java.util.TimerTask

class MyObservableCounter(activity: LifecycleOwner):LifecycleEventObserver {
    private var secendCount=0
    private lateinit var timer: Timer
    private var _currentCounter:MutableLiveData<Int> = MutableLiveData()
    val currentCounter:LiveData<Int>
        get() = _currentCounter
    init{
        activity.lifecycle.addObserver(this)
    }
    private fun startTimer(){
        timer=Timer()
        val task=object :TimerTask(){
            override fun run(){
                secendCount++
                _currentCounter.postValue(secendCount)
                Log.i("MyObservableCounter","secendCount=$secendCount")
            }
        }
        timer.schedule(task,0,1000)

    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event){
            Lifecycle.Event.ON_START->{
                Log.i("MyObservableCounter","timer is started")
                startTimer()
            }
            Lifecycle.Event.ON_STOP->{
                Log.i("MyObservableCounter","Timer is stopped")
                timer.cancel()
            }
            else -> {}
        }
    }
}
