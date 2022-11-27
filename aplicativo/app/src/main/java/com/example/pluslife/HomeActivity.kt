package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityHomeBinding
import com.example.pluslife.models.enum.UsuarioSharedSecret

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        prefs = getSharedPreferences("DADOS", MODE_PRIVATE)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvComoDoar.setOnClickListener{ trocarTela(ComoDoarActivity()) }
        binding.tvQuemDoar.setOnClickListener{ trocarTela(QuemPodeDoar()) }

        binding.btnPesquisarLugares.setOnClickListener {

            val isLogado = prefs.getBoolean(UsuarioSharedSecret.USUARIO_LOGADO.toString(),false)

            if(isLogado){
                trocarTela(BancosProximosActivity())
            }else {
                trocarTela(BuscarEnderecoActivity())
            }
        }
        navbar()

    }

    private fun navbar() {
        binding.navPerfil.setOnClickListener {
            val isLogado = prefs.getBoolean(UsuarioSharedSecret.USUARIO_LOGADO.toString(),false)

            if(isLogado){
                trocarTela(PerfilActivity())
            }else {
                trocarTela(LoginActivity())
            }
        }
        binding.navPontos.setOnClickListener {
            val isLogado = prefs.getBoolean(UsuarioSharedSecret.USUARIO_LOGADO.toString(),false)

            if(isLogado){
                trocarTela(BancosProximosActivity())
            }else {
                trocarTela(BuscarEnderecoActivity())
            }
        }
    }


    fun trocarTela(tela: Activity){
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }

}