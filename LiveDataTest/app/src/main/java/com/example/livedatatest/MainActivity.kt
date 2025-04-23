package com.example.livedatatest

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var tvInfo: TextView
    lateinit var btnUpdateTime: Button
    lateinit var tvCounter: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvCounter=findViewById(R.id.tvCounter)
        btnUpdateTime=findViewById(R.id.btnUpdateTime)
        tvInfo=findViewById(R.id.tvInfo)
        val dataClass=DataClass()
        val infoObserver=Observer<String>{
            tvInfo.text=it
        }
        dataClass.info.observe(this,infoObserver)
        btnUpdateTime.setOnClickListener{
            dataClass.info.value="Time: ${Date().toString()}"
        }
        val timer=MyObservableCounter(this)
        timer.currentCounter.observe(this, Observer {
            tvCounter.text=it.toString()
        })
    }
}