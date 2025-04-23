package com.example.dispatchertest

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.dispatchertest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
const val TAG = "ScopeInAndroid"

fun showLog(info: Any?) {
    info?.let {
        Log.d(TAG, info.toString())
    }
}

//class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//        with(binding){
//            btnUseMain.setOnClickListener {
//                testDispatcherMain()
//            }
//            btnUseMainImmediate.setOnClickListener {
//                testDispatcherMainImmediate()
//            }
//        }
//    }
//    private val coroutineScope= CoroutineScope(Dispatchers.Main)
//    private fun testDispatcherMain(){
//        binding.tvInfo.text=""
//        with(coroutineScope){
//            launch(Dispatchers.Main){
//                binding.tvInfo.append("\n第一条语句\n")
//            }
//            launch(Dispatchers.Main){
//                binding.tvInfo.append("\n第二条语句\n")
//            }
//            launch(Dispatchers.Main){
//                binding.tvInfo.append("\n第三条语句\n")
//            }
//        }
//    }
//    private fun testDispatcherMainImmediate(){
//        binding.tvInfo.text=""
//        with(coroutineScope){
//            launch(Dispatchers.Main){
//                binding.tvInfo.append("\n第一条语句\n")
//            }
//            launch(Dispatchers.Main){
//                binding.tvInfo.append("\n第二条语句\n")
//            }
//            launch(Dispatchers.Main.immediate){
//                binding.tvInfo.append("\n第三条语句\n")
//            }
//        }
//    }
//}
class MainActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        useMainScope()

        showLog("MainActivity:onCreate")

        val vm= ViewModelProvider(this).get(MyViewModel::class.java)
        vm.useCoroutine()
    }
    override fun onStart(){
        super.onStart()
        showLog("MainActivity:onStart")
    }
    override fun onResume(){
        super.onResume()
        showLog("MainActivity:onResume")
    }
    fun useMainScope(){
        MainScope().launch {
            val threadInfo="MainScope中启动的协程,"+"使用线程：${Thread.currentThread().name}"
            showLog(threadInfo)
        }
    }
    fun useLifecycleScope(){
        lifecycleScope.launch{
            showLog("in lifecycleScope.launch")
        }
        lifecycleScope.launchWhenStarted {
            showLog("in lifecycleScope.launchWhenStarted")
        }
        lifecycleScope.launchWhenCreated {
            showLog("in lifecycleScope.launchWhenCreated")
        }
    }
}