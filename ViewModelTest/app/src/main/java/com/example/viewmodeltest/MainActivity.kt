package com.example.viewmodeltest

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel
    private lateinit var myViewModelFactory: MyViewModelFactory
    private lateinit var scoreTextView: TextView
    private lateinit var btnChangeScore: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        scoreTextView = findViewById(R.id.scoreTextView)
        btnChangeScore= findViewById(R.id.btnChangeScore)

        //TODO 在MyViewModel中添加一个score属性，类型为MutableLiveData<Int>，初始值为0
        myViewModelFactory= MyViewModelFactory(100)
        viewModel = ViewModelProvider(this,myViewModelFactory).get(MyViewModel::class.java)
        //TODO score为LiveData，观察score属性的变化，并在变化时更新UI
        viewModel.score.observe(this) { score ->
            // Update the UI with the new score
            scoreTextView.text = score.toString()
        }
        btnChangeScore.setOnClickListener{
            val initScore=viewModel.score.value?:0
            viewModel.score.value=initScore+1
        }

    }
}