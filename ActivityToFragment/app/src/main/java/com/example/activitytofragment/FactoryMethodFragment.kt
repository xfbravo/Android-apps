package com.example.activitytofragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FactoryMethodFragment: Fragment() {
    private var param1:String?=null
    private var param2:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1=it.getString("param1")
            param2=it.getString("param2")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_factory_method, container, false)
        if(param1!=null&&param2!=null){
            view.findViewById<TextView>(R.id.tvFactoryMethod).text="$param1\n$param2"
        }
        return view
    }
    companion object{
        fun newInstance(param1:String,param2:String):FactoryMethodFragment{
            val fragment=FactoryMethodFragment()
            fragment.apply {
                arguments=Bundle().apply {
                    putString("param1",param1)
                    putString("param2",param2)
                }
            }
            return fragment
        }
    }
}