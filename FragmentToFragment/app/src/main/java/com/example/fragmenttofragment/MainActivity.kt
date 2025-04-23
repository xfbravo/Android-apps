package com.example.fragmenttofragment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class MainActivity : AppCompatActivity(), InputFragment.SendDataToShowFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if(savedInstanceState==null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, InputFragment())
                .commit()
        }
    }

    override fun sendData(data: String) {
        val message=Bundle()
        message.putString(MESSAGE_KEY,data)
        val showFragment = ShowFragment()
        showFragment.arguments=message
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, showFragment)
            .commit()
    }
}