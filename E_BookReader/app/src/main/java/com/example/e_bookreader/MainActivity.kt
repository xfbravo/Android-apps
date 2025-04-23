package com.example.e_bookreader

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<ImageView>(R.id.lovinghotfamily_bookcover).setOnClickListener{
            val intent= Intent(this,BookReaderActivity::class.java)
            intent.putExtra("BOOK_ID",R.raw.lovinghotfamily)
            intent.putExtras(putBookNameToBundle("Loving Hot Family"))
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.book_cover).setOnClickListener{
            val intent=Intent(this,BookReaderActivity::class.java)
            intent.putExtra("BOOK_ID",R.raw.book1)
            intent.putExtras(putBookNameToBundle("Book 1"))
            startActivity(intent)
        }
    }
    fun putBookNameToBundle(bookName:String):Bundle{
        val bundle=Bundle()
        bundle.putString("bookName",bookName)
        return bundle
    }
}