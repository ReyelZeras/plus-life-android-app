package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityQuemPodeDoarBinding
import com.example.pluslife.models.enum.DadosSharedSecret

class QuemPodeDoar : AppCompatActivity() {

    lateinit var binding: ActivityQuemPodeDoarBinding
    lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuemPodeDoarBinding.inflate(layoutInflater)
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