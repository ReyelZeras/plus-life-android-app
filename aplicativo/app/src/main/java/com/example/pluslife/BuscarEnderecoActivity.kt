package com.example.pluslife

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityBuscarEnderecoBinding
import com.example.pluslife.databinding.ActivityHomeBinding

class BuscarEnderecoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuscarEnderecoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_endereco)

        binding = ActivityBuscarEnderecoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navbar()

    }

    private fun navbar() {
        binding.navPerfil.setOnClickListener { trocarTela(PerfilActivity()) }
        binding.navPontos.setOnClickListener { trocarTela(BancosProximosActivity()) }
    }

    fun trocarTela(tela: Activity){
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }
}