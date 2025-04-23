package com.example.fragmentwithviewmodel

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: BlankViewModel
    private lateinit var btnClickMe: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnClickMe= findViewById(R.id.btnClickMe)
        btnClickMe.setOnClickListener{
            val currentCount= viewModel.counter.value?:0
            if (currentCount != null) {
                viewModel.changeCounter(currentCount + 1)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        val fragment= supportFragmentManager.findFragmentByTag("fragment_blank")
        viewModel=(fragment as BlankFragment).viewModel
    }
}