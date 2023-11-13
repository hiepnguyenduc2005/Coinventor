package com.example.coinvertor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coinvertor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setupClickListeners()
    }

    /*private fun setupClickListeners(){
        compute.setOnClickListener { computeButtonClicked() }
    }

    private fun computeButtonClicked() {
        try {
            val result = numberInput.pow(powerInput)
            output.text = result.toString()
        } catch (e: NumberFormatException) {
            output.text = "Invalid input" // Handle invalid input gracefully
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        runningCountJob?.cancel()
    }*/
}