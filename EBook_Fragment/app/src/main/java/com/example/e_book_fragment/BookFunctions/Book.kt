package com.example.e_book_fragment.BookFunctions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_book_fragment.R
//TODO 定义书籍类
data class Book (
    val title:String,
    val coverResId:Int,
    val rawResId:Int
)

class BookAdapter(
    private val context: Context,
    private val books:List<Book>,
    private val onItemClick:(Book) -> Unit
):RecyclerView.Adapter<BookAdapter.BookViewHolder>(){//TODO 继承RecyclerView.Adapter<BookAdapter.BookViewHolder>
    inner class BookViewHolder(view: View):RecyclerView.ViewHolder(view){
        val imageView: ImageView = view.findViewById(R.id.bookImageView)
        val textview: TextView = view.findViewById(R.id.bookTitle)
        fun bind(book:Book){
            imageView.setImageResource(book.coverResId)
            textview.text = book.title
            itemView.setOnClickListener{
                onItemClick(book)
            }
        }
    }
//TODO 创建ViewHolder,绑定item_book布局
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.item_book,parent,false)
        return BookViewHolder(view)
    }
//TODO 绑定数据
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }
//TODO 返回书籍数量
    override fun getItemCount(): Int {
        return books.size
    }

}