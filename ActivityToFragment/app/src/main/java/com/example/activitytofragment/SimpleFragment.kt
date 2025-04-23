package com.example.activitytofragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
const val MESSAGE_KEY="message"
class SimpleFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_simple, container, false)
        val message=arguments?.getString(MESSAGE_KEY)?:"No message"
        val tvMessage=view.findViewById<TextView>(R.id.tvMessage)
        if(tvMessage!=null){
            tvMessage.text=message
        }
        return view
    }
}