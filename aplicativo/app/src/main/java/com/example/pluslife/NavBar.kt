package com.example.pluslife

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pluslife.databinding.NavbarBinding

class NavBar : AppCompatActivity() {
    private lateinit var binding: NavbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NavbarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navHome.setOnClickListener{ telaHome() }
    }

    fun telaHome(){
        val telaHome = Intent(
            this,
            ComoDoarActivity::class.java
        )
        startActivity(telaHome)
    }
}