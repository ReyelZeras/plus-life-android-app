package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityComoDoarBinding
import com.example.pluslife.models.enum.DadosSharedSecret

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
        binding.navPerfil.setOnClickListener {
            val isLogado = prefs.getBoolean(DadosSharedSecret.USUARIO_LOGADO.toString(),false)

            if(isLogado){
                trocarTela(PerfilActivity())
            }else {
                trocarTela(LoginActivity())
            }
        }
        binding.navPontos.setOnClickListener {
            val isLogado = prefs.getBoolean(DadosSharedSecret.USUARIO_LOGADO.toString(),false)

            if(isLogado){
                trocarTela(BancosProximosActivity())
            }else {
                trocarTela(BuscarEnderecoActivity())
            }
        }
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