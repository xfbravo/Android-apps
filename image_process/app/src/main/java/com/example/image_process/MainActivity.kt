package com.example.image_process

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var image=findViewById<ImageView>(R.id.image)
        var btnGirl=findViewById<Button>(R.id.pretty)
        var btnBoy=findViewById<Button>(R.id.handsome)
        var tvInfo=findViewById<TextView>(R.id.ask)
        var exit=findViewById<Button>(R.id.Exit)
        btnGirl.setOnClickListener{
            image.setImageResource(R.drawable.pretty)
            tvInfo.text="这是你吗？热巴"
        }
        btnBoy.setOnClickListener{
            image.setImageResource(R.drawable.handsome)
            tvInfo.text="这是你吗？彦祖"
        }
        exit.setOnClickListener{
            finish()
        }
    }
}

