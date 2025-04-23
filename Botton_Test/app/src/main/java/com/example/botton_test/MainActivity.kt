package com.example.botton_test

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var btnChangeColor = findViewById<Button>(R.id.button)
        var check=true
        btnChangeColor.setOnClickListener {
            if(check){
                btnChangeColor.setTextColor(resources.getColor(R.color.red))
                (it as Button).text = "red"
                check=false

            }
            else{
                btnChangeColor.setTextColor(resources.getColor(R.color.blue))
                (it as Button).text = "blue"
                check=true
            }
        }
    }

}