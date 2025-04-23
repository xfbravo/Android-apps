package com.example.rolldice

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rolldice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModelRollDice
    private lateinit var imageViews:Array<ImageView>
    private val drawables=arrayOf(
        R.drawable.dice_1,R.drawable.dice_2,
        R.drawable.dice_3,R.drawable.dice_4,
        R.drawable.dice_5,R.drawable.dice_6
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageViews=arrayOf(binding.dice1,binding.dice2,binding.dice3,binding.dice4,binding.dice5)
        viewModel= ViewModelProvider(this).get(ViewModelRollDice::class.java)
        viewModel.dieValue.observe(this,Observer{
            imageViews[it.first].setImageResource(drawables[it.second-1])
        })
        binding.btnRoll.setOnClickListener {
            viewModel.rollTheDice()
        }
    }
}