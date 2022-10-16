package com.example.pluslife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityHomeBinding
import com.example.pluslife.databinding.ActivityLoginBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvComoDoar.setOnClickListener{ telaComoDoar() }
    }

    private fun telaComoDoar() {
        val telaComoDoar = Intent(
            this,
            ComoDoarActivity::class.java
        )
        startActivity(telaComoDoar)
    }
}