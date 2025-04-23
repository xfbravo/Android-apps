package com.example.fragmenttest

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var count=2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        count=savedInstanceState?.getInt("count")?:2
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnAdd=findViewById<View>(R.id.btnAdd)
        val btnRemove=findViewById<View>(R.id.btnRemove)
        btnAdd.setOnClickListener{
            val fragment=FirstFragment.newInstance(count)
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragmentContainerView, fragment,"first$count")
                .commit()
            count++
        }
        btnRemove.setOnClickListener{
            val fragment=supportFragmentManager.findFragmentByTag("first${count-1}")
            if(fragment!=null){
                supportFragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit()
                count--
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count",count)
    }
}