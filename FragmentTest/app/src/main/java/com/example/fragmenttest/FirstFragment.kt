package com.example.fragmenttest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FirstFragment(): Fragment() {
    private var count:Int=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_first, container, false)
        val textView=view.findViewById<TextView>(R.id.firstFragmentTextView)
        count=arguments?.getInt("count")?:1
        textView.text="Fragment $count"
        return view
    }

    companion object{
        fun newInstance(count:Int):FirstFragment{
            val args=Bundle()
            args.putInt("count",count)
            val fragment=FirstFragment()
            fragment.arguments=args
            return fragment
        }
    }


}