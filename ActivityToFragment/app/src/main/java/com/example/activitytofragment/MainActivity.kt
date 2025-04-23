package com.example.activitytofragment

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Date

class MainActivity : AppCompatActivity() {
    val MESSAGE_KEY="message"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnSpecialFragment=findViewById<View>(R.id.btnSpecialFragment)
        btnSpecialFragment.setOnClickListener{
            val specialFragment=SpecialFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, specialFragment)
                .commit()
            specialFragment.receiveMessage("当前时间${Date()}")
        }
        val btnUseArguments=findViewById<View>(R.id.btnUseArguments)
        btnUseArguments.setOnClickListener{
            val arguments=Bundle()
            arguments.putString(MESSAGE_KEY, "Activity通过Bundle传递的消息")
            val fragment=SimpleFragment()
            fragment.arguments=arguments
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit()
        }
        val btnFactoryMethod=findViewById<View>(R.id.btnFactoryMethod)
        btnFactoryMethod.setOnClickListener{
            val fragment=FactoryMethodFragment.newInstance("Factory Method","使用示例")
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit()
        }
    }
}