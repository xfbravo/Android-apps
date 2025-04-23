package com.example.e_bookreader

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import androidx.appcompat.widget.Toolbar
class BookReaderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_reader)
        val bookName = intent.getStringExtra("bookName")
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title=bookName

        val bookId = intent.getIntExtra("BOOK_ID", -1)
        if (bookId != -1) {
            val bookContent = readRawTextFile(bookId)
            findViewById<TextView>(R.id.book_content).text = bookContent
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_book_reader, menu)
        return true
    }
    var mode:String="light"

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_settings->{

                true
            }
            R.id.action_switch_mode->{
                if(mode=="light"){
                    item.title="日间模式"
                    switchMode("light")
                    mode="dark"
                }
                else{
                    item.title="夜间模式"
                    switchMode("dark")
                    mode="light"
                }
                true
            }
            R.id.action_chapterChoose->{
                showChapterDialog()
                true
            }
            else-> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    private fun showChapterDialog() {
        val chapters = readChapter(R.raw.lovinghotfamily) // 假设你的txt文件在res/raw目录下
        val chapterTitles = chapters.mapIndexed { index, _ -> "Chapter ${index + 1}" }.toTypedArray()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("选择章节")
        builder.setItems(chapterTitles) { dialog, which ->
            val selectedChapter = chapters[which]
            findViewById<TextView>(R.id.book_content).text = selectedChapter
        }
        builder.show()
    }
    private fun readChapter(resId: Int): List<String> {
        val inputStream: InputStream = resources.openRawResource(resId)
        val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
        val chapters = mutableListOf<String>()
        val content = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            if (line!!.startsWith("Chapter")) {
                if (content.isNotEmpty()) {
                    chapters.add(content.toString())
                    content.clear()
                }
            }
            content.append(line).append("\n")
        }
        if (content.isNotEmpty()) {
            chapters.add(content.toString())
        }
        reader.close()
        return chapters
    }
    fun switchMode(mode:String){
        if(mode=="light"){
            val bookContent = findViewById<TextView>(R.id.book_content)
            bookContent.setBackgroundColor(resources.getColor(R.color.black))
            bookContent.setTextColor(resources.getColor(R.color.white))
            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            toolbar.setBackgroundColor(resources.getColor(R.color.black))
            toolbar.setTitleTextColor(resources.getColor(R.color.white))
        }
        else{
            val bookContent = findViewById<TextView>(R.id.book_content)
            bookContent.setBackgroundColor(resources.getColor(R.color.white))
            bookContent.setTextColor(resources.getColor(R.color.black))
            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            toolbar.setBackgroundColor(resources.getColor(R.color.white))
            toolbar.setTitleTextColor(resources.getColor(R.color.black))
        }
    }
    private fun readRawTextFile(resId: Int): String {
        val inputStream:InputStream = resources.openRawResource(resId)
        val reader:BufferedReader = BufferedReader(InputStreamReader(inputStream))
        val content = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            content.append(line).append("\n")
        }
        reader.close()
        return content.toString()
    }

}