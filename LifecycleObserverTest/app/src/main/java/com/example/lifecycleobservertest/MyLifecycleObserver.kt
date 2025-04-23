package com.example.lifecycleobservertest

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class MyLifecycleObserver(private val timer: TextView) :LifecycleEventObserver{
    private val handler = Handler(Looper.getMainLooper())
    private var seconds = 0
    private val runnable = object :Runnable{
        override fun run(){
            seconds++
            timer.text = seconds.toString()
            handler.postDelayed(this, 1000)
        }
    }
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event){
            Lifecycle.Event.ON_CREATE -> {
                handler.post(runnable)
                Log.d("MyLifecycleObserver", "ON_CREATE")

            }
            Lifecycle.Event.ON_START -> {
                Log.d("MyLifecycleObserver", "ON_START")
            }
            Lifecycle.Event.ON_RESUME -> {
                Log.d("MyLifecycleObserver", "ON_RESUME")
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.d("MyLifecycleObserver", "ON_PAUSE")
            }
            Lifecycle.Event.ON_STOP -> {
                Log.d("MyLifecycleObserver", "ON_STOP")
            }
            Lifecycle.Event.ON_DESTROY -> {
                Log.d("MyLifecycleObserver", "ON_DESTROY")
                handler.removeCallbacks(runnable)
            }
            Lifecycle.Event.ON_ANY -> {
                Log.d("MyLifecycleObserver", "ON_ANY")
            }
        }
    }
}