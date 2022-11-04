package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityPerfilBinding
import com.example.pluslife.databinding.ActivityLoginBinding
import com.example.pluslife.models.DoadorModel
import com.example.pluslife.models.enum.DadosSharedSecret
import java.time.LocalDate

class PerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences("DADOS", MODE_PRIVATE)

        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nomeDoador = prefs.getString(DadosSharedSecret.USUARIO_NOME.toString(), "doador")
        binding.titulo.text = "Olá, $nomeDoador"

        navbar()
        binding.btnVoltar.setOnClickListener { trocarTela(HomeActivity()) }
        binding.edtNome.setOnClickListener { trocarTela(AtualizarNomeActivity()) }
        binding.edtEmail.setOnClickListener { trocarTela(AtualizarEmail()) }
        binding.edtEndereco.setOnClickListener { trocarTela(AtualizarEnderecoActivity()) }
    }

    private fun navbar() {
        binding.navHome.setOnClickListener { trocarTela(HomeActivity()) }
        binding.navPerfil.setOnClickListener { trocarTela(PerfilActivity()) }
        binding.navPontos.setOnClickListener { trocarTela(ComoDoarActivity()) }
    }

    fun trocarTela(tela: Activity){
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }


}