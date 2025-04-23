package com.example.helloandriod

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var tvInfo=findViewById<TextView>(R.id.textView)
        var tvInfo2=findViewById<TextView>(R.id.textView2)
        var bottom2=findViewById<Button>(R.id.button2)
        var counter=0
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var Size:Float=tvInfo.textSize
        bottom2.setOnClickListener{
            counter++
            tvInfo2.text="点击了"+counter.toString()+"次"
            Size+=1f
            tvInfo.textSize=Size
        }
    }
}