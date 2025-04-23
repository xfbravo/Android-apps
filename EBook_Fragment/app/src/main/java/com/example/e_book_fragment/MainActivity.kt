package com.example.e_book_fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager.BackStackEntry
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_book_fragment.BookFunctions.Book
import com.example.e_book_fragment.BookFunctions.BookAdapter


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val libraryTextView = findViewById<TextView>(R.id.library)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 5)
        val bookList = listOf(
            Book("What if", R.drawable.book_cover_image, R.raw.book1),
            Book("Loving Hot Family", R.drawable.lovinghotfamily_bookcover, R.raw.lovinghotfamily),
            Book("球状闪电", R.drawable.lightning_ball, R.raw.lightning_ball),
            Book("乡村教师", R.drawable.country_teacher, R.raw.country_teacher),
        )
        //TODO 将书籍列表传递给adapter
        val adapter = BookAdapter(this, bookList) { book ->//TODO 点击书籍后跳转到ReaderFragment
            val readerFragment = ReaderFragment()
            val arguments = Bundle()
            arguments.apply {//TODO 传递书籍信息
                putString("bookTitle", book.title)
                putInt("bookRawResId", book.rawResId)
            }
            readerFragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                .replace(R.id.readerFragmentContainerView, readerFragment)
                .addToBackStack(null)
                .commit()
            //TODO 点击书籍后隐藏recyclerView和libraryTextView，不然会显示在ReaderFragment上
            recyclerView.visibility = RecyclerView.GONE
            libraryTextView.visibility = TextView.GONE
        }
        //TODO 将adapter设置到recyclerView，即显示书籍列表
        recyclerView.adapter = adapter
        //TODO 检查是否返回主界面，如果是则显示recyclerView和libraryTextView
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                recyclerView.visibility = RecyclerView.VISIBLE
                libraryTextView.visibility = TextView.VISIBLE
            } else {
                recyclerView.visibility = RecyclerView.GONE
                libraryTextView.visibility = TextView.GONE
            }
        }
    }
}