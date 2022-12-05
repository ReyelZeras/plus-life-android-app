package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityComoDoarBinding
import com.example.pluslife.models.enum.UsuarioSharedSecret

class ComoDoarActivity : AppCompatActivity() {

    lateinit var binding: ActivityComoDoarBinding
    lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComoDoarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = getSharedPreferences("DADOS", MODE_PRIVATE)
        navbar()
    }

    private fun navbar() {
        binding.navHome.setOnClickListener { trocarTela(HomeActivity()) }
        binding.navPerfil.setOnClickListener { trocarTela(PerfilActivity())}
        binding.navPontos.setOnClickListener { trocarTela(BancosProximosActivity())}
    }


    fun trocarTela(tela: Activity){
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }
}