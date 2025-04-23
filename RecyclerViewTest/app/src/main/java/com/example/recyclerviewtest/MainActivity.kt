package com.example.recyclerviewtest

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity(),MyClickListener {
    private lateinit var tvInfo:TextView
    private lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        tvInfo= findViewById(R.id.tvInfo)
        recyclerView= findViewById(R.id.recyclerView)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView.layoutManager= LinearLayoutManager(this)
        val adapter= MyDataAdapter(createData(),this)
        recyclerView.adapter=adapter
    }
    override fun onClickRow(id: Int) {
        tvInfo.text="对象id=${id}的行被点击了"
    }
    override fun onClickButtonInRow(message: String) {
        tvInfo.text=message
    }
    fun createData():List<MyData> {
        val data= mutableListOf<MyData>()
        for(i in 0..20){
            val ran=Random.nextInt(0,20)
            data.add(MyData(ran,"内容${ran}"))
        }
        return data

    }
}