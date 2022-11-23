package com.example.pluslife

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityQuemPodeDoarBinding

class QuemPodeDoar : AppCompatActivity() {

    lateinit var binding: ActivityQuemPodeDoarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuemPodeDoarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navbar()
    }

    private fun navbar() {
        binding.navPerfil.setOnClickListener { trocarTela(PerfilActivity()) }
        binding.navPontos.setOnClickListener { trocarTela(BancosProximosActivity()) }
        binding.navHome.setOnClickListener { trocarTela(HomeActivity()) }
    }

    fun trocarTela(tela: Activity){
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }
}