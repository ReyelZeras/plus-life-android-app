package com.example.pluslife

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityBancoEspecificoBinding
import com.example.pluslife.databinding.ActivityBuscarEnderecoBinding
import com.example.pluslife.models.NomeNivelSang

class BancoEspecificoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBancoEspecificoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banco_especifico)

        binding = ActivityBancoEspecificoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVoltar.setOnClickListener { trocarTela(BancosProximosActivity()) }
        navbar()

        val nome = intent.getStringExtra("nome")
        val email = intent.getStringExtra("email")
        val telefone = intent.getStringExtra("telefone")
        val endereco = intent.getStringExtra("endereco")
        var estoque: ArrayList<NomeNivelSang> = intent.getSerializableExtra("estoque")  as ArrayList<NomeNivelSang>

        binding.tvNome.text = nome
        binding.tvEmail.text = email
        binding.tvTelefone.text = telefone
        binding.tvEndereco.text= endereco


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

    fun definirEstoque(estoque: ArrayList<NomeNivelSang>){



    


    }
}