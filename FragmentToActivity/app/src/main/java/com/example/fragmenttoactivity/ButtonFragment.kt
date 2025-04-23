package com.example.fragmenttoactivity

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button



class ButtonFragment : Fragment() {

    //定义回调接口
    interface ResponseToFragmentButtonClick{
        fun responseToClick(clickCount:Int)
    }

    var btnClickMe:Button?=null
    var counter:Int=0
    //用于引用外部监听者对象
    private var listener:ResponseToFragmentButtonClick?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //将外部Activity设置为监听者
        if (context is ResponseToFragmentButtonClick) {
            listener = context
        } else {
            throw RuntimeException("${context} 必须实现 ResponseToFragmentButtonClick接口")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root= inflater.inflate(R.layout.fragment_button, container, false)
        btnClickMe=root.findViewById(R.id.btnClickMe)
        //当点触按钮时，回调外部Activity的方法
        btnClickMe?.setOnClickListener {
            counter++
            //如果有外部监听者，则回调之
            listener?.responseToClick(counter)
        }
        return root
    }

}
