package com.example.coroutinewithviewactivity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
var TAG = "AndroidCoroutine"
class MainActivity : AppCompatActivity() {
    private lateinit var btnRun: Button
    private lateinit var tvInfo: TextView
    private var job :Job?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        btnRun=findViewById(R.id.btnRun)
        tvInfo=findViewById(R.id.tvInfo)
        btnRun.setOnClickListener {
            job=MainScope().launch{
                btnRun.isEnabled=false
                tvInfo.text="协程以及启动"
                val result=calculateUseCoroutine()
                tvInfo.text="\n计算结果为：${result},\n协程:${job}"
                btnRun.isEnabled=true
            }

        }
    }
    private suspend fun process():String{
        return withContext(Dispatchers.IO){
            "\nCoroutine uses thread\n${Thread.currentThread().name}to work"
        }
    }

    private suspend fun calculateUseCoroutine():Long{
        return withContext(Dispatchers.Default){
            var sum:Long=0
            for(i in 1..100){
                sum+=i
                delay(80)
                if(i%10==0){
                    //切换到UI线程显示处理进度
                    showProgress(i)
                    Log.i(TAG,"正在处理${i},${Thread.currentThread().name}")
                }
            }
            sum
        }
    }

    suspend fun showProgress(percent:Int){
        withContext (Dispatchers.Main){
            tvInfo.text="$percent%"
        }
    }
    override fun onStop(){
        super.onStop()
        job?.cancel()
        Log.d(TAG,"onStop被调用，协程：$job")
        btnRun.isEnabled=true
    }
}
