package com.example.fragmentwithviewmodel

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class BlankFragment : Fragment() {

    companion object {
        fun newInstance() = BlankFragment()
    }

    private lateinit var root:View
    private lateinit var tvInfo:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_blank, container, false)
        return root
    }

    //引用ViewModel对象
    lateinit var viewModel: BlankViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvInfo = root.findViewById(R.id.tvInfo)
        //实例化ViewModel，并将引用显露给外界
        viewModel = ViewModelProvider(this)
            .get(BlankViewModel::class.java)

        val updateCounter = Observer<Int> {
            //使用文本控件显示计数器的当前值
            tvInfo?.text = it.toString()
        }
        //监控计数器值的变化
        viewModel.counter.observe(viewLifecycleOwner, updateCounter)
    }
}