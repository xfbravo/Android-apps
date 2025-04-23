package com.example.activitytofragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
class SpecialFragment: Fragment() {
    private var tvInfo:TextView? = null
    private var message:String? = null
    override fun onCreateView(
        inflater:LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view = inflater.inflate(R.layout.fragment_special, container, false)
        tvInfo = view.findViewById(R.id.tvMessage)
        tvInfo?.text = message
        return view
    }

    fun receiveMessage(message:String){
        this.message = message
    }
}