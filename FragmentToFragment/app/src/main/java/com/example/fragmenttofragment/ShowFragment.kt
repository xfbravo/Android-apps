package com.example.fragmenttofragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.fragment.app.Fragment
const val MESSAGE_KEY:String="message"
class ShowFragment:Fragment() {
    private lateinit var textView: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_show,container,false)
        textView=view.findViewById<TextView>(R.id.textView)
        val messageText=arguments?.getString(MESSAGE_KEY)
        textView.text=messageText
        return view
    }
    fun updateData(message: String){
        val tvInfo=view?.findViewById<TextView>(R.id.textView)
        tvInfo?.text=message
    }
}