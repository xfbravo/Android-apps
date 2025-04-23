package com.example.coroutineandviewmodel

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.coroutineandviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel= ViewModelProvider(this).get(MainViewModel::class.java)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnRun.setOnClickListener {
            if(viewModel.currentState.value!=WorkState.WORKING){
                viewModel.doWork()
            }else{
                viewModel.cancel()
            }
        }
        viewModel.workingInfo.observe(this){
            binding.tvInfo.text=it
        }
        viewModel.currentState.observe (this){
            when(it){
                WorkState.WORKING->{
                    binding.pgWorking.visibility=View.VISIBLE
                    binding.btnRun.text="取消"
                }
                WorkState.IDLE->{
                    binding.pgWorking.visibility=View.INVISIBLE
                    binding.btnRun.text="运行"
                }
                WorkState.CANCELLED->{
                    binding.pgWorking.visibility=View.INVISIBLE
                    binding.btnRun.text="运行"
                    binding.tvInfo.text="工作已取消"
                }
            }
        }
    }
}