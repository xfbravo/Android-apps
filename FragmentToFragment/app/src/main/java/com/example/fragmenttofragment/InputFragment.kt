package com.example.fragmenttofragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class InputFragment: Fragment() {
    interface SendDataToShowFragment {
        fun sendData(data: String)
    }
    private var listener: SendDataToShowFragment? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SendDataToShowFragment) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement SendDataToShowFragment.")
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    private fun passData(message: String) {
        listener?.sendData(message)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_input,container,false)
        val btnSendMessage=view.findViewById<Button>(R.id.btnSendMessage)
        val editText=view.findViewById<EditText>(R.id.editText)
        btnSendMessage.setOnClickListener {
            val message=editText.text.toString()
            passData(message)
        }
        return view
    }
}