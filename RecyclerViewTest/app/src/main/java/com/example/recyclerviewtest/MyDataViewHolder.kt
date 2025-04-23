package com.example.recyclerviewtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyDataViewHolder private constructor(
    private val root: View,
    private val listener: MyClickListener?
):RecyclerView.ViewHolder(root){
    private var idTextView:TextView=root.findViewById(R.id.tvID)
    private var contentTextView:TextView=root.findViewById(R.id.tvContent)
    private var clickButton: Button =root.findViewById(R.id.btnRow)
    fun bind(data:MyData){
        idTextView.text=data.id.toString()
        if(data.id>10){
            idTextView.setBackgroundResource(R.color.red)
        }
        contentTextView.text=data.content
        root.tag=data
        if(listener!=null){
            root.setOnClickListener {
                listener.onClickRow(data.id)
            }
            clickButton.setOnClickListener {
                listener.onClickButtonInRow(data.toString())
            }
        }
    }
    companion object{
        fun createViewHolder(
            parent: ViewGroup,
            listener: MyClickListener?
        ):MyDataViewHolder{
            val layoutInflater=LayoutInflater.from(parent.context)
            val root=layoutInflater.inflate(R.layout.list_item,parent,false)
            return MyDataViewHolder(root,listener)
        }
    }
}