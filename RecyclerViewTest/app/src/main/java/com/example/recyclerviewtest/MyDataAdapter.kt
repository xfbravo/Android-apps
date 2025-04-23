package com.example.recyclerviewtest
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyDataAdapter(
    private val data: List<MyData>,
    private val listener: MyClickListener?
):RecyclerView.Adapter<MyDataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDataViewHolder {
        return MyDataViewHolder.createViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: MyDataViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
