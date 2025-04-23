package com.example.e_book_fragment.BookFunctions

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.e_book_fragment.R
import com.example.e_book_fragment.ReaderFragment

class PageAdapter(private var pages: List<String>, private val listener: OnPageClickListener) :
    RecyclerView.Adapter<PageAdapter.PageViewHolder>() {
    interface OnPageClickListener {
        fun onPageClick()
    }
    private var textSize=16f
    fun setTextSize(size:Float){
        textSize=size
        notifyDataSetChanged()
    }
    inner class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pageContent: TextView = itemView.findViewById(R.id.pageContent)//TODO 显示书籍内容
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        //TODO 加载item_page布局
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        return PageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.pageContent.text = pages[position]//TODO 显示书籍内容
        holder.pageContent.tag="page_$position"//TODO 设置tag
        holder.pageContent.textSize=textSize//TODO 设置字体大小
        holder.pageContent.setOnClickListener {
            listener.onPageClick()
        }
    }

    override fun getItemCount(): Int = pages.size

}