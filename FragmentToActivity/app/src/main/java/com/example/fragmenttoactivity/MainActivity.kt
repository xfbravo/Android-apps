package com.example.fragmenttoactivity

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(),
    ButtonFragment.ResponseToFragmentButtonClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //显示Fragment
        val fragment = ButtonFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, fragment)
            .commit()
    }

    //供Fragment回调的方法
    override fun responseToClick(clickCount: Int) {
        val tvInfo: TextView =findViewById(R.id.tvInfo)
        tvInfo.text = clickCount.toString()
    }

}
