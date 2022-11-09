package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import androidx.core.content.edit
import com.example.pluslife.databinding.ActivityPerfilBinding
import com.example.pluslife.databinding.ActivityLoginBinding
import com.example.pluslife.models.DoadorModel
import com.example.pluslife.models.enum.DadosSharedSecret.*
import java.time.LocalDate

class PerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences("DADOS", MODE_PRIVATE)

        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nomeDoador = prefs.getString(USUARIO_NOME.toString(), "doador")
        binding.titulo.text = "Olá, $nomeDoador"

        navbar()
        iniciarLabels()
        binding.btnVoltar.setOnClickListener { trocarTela(HomeActivity()) }
        binding.edtNome.setOnClickListener { trocarTela(AtualizarNomeActivity()) }
        binding.edtEmail.setOnClickListener { trocarTela(AtualizarEmail()) }
        binding.edtTipoSang.setOnClickListener { trocarTela(AtualizarTipoSanguineoActivity()) }
        binding.edtEndereco.setOnClickListener { trocarTela(AtualizarEnderecoActivity()) }
        binding.btnSair.setOnClickListener {
            val editor = prefs.edit()
            editor.clear()
            editor.apply()
            trocarTela(MainActivity())
        }
    }

    private fun iniciarLabels() {
        binding.tvNomeAtual.text = prefs.getString(USUARIO_NOME.toString(), "Reyel Zapateiro Magalhães 2")
        binding.tvEmailAtual.text = prefs.getString(USUARIO_EMAIL.toString(), "email@gmail.com")
        binding.tvTipoSangAtual.text = prefs.getString(USUARIO_TIPO_SANGUINEO.toString(), "AB-")
        binding.tvEnderecoAtual.text = montarEnderecoAtual()
    }
    private fun montarEnderecoAtual(): String {
        val rua = prefs.getString(ENDERECO_RUA.toString(), "Rua Haddok Lobo")
        val numero = prefs.getInt(ENDERECO_NUMERO.toString(), 353)
        val bairro = prefs.getString(ENDERECO_BAIRRO.toString(), "Consolação")
        val cidade = prefs.getString(ENDERECO_CIDADE.toString(), "São Paulo")
        val estado = prefs.getString(ENDERECO_ESTADO.toString(), "SP")
        return "$rua, $numero, $bairro, $cidade - $estado"
    }


    private fun navbar() {
        binding.navHome.setOnClickListener { trocarTela(HomeActivity()) }
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