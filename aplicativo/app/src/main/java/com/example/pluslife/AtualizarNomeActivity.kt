package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityAtualizarNomeBinding
import com.example.pluslife.models.DoadorModel
import com.example.pluslife.models.enum.DadosSharedSecret
import com.example.pluslife.services.AtualizarService
import java.time.LocalDate

class AtualizarNomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityAtualizarNomeBinding
    lateinit var prefs: SharedPreferences
    lateinit var atualizarService: AtualizarService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarNomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("DADOS", AppCompatActivity.MODE_PRIVATE)

        binding.btnVoltar.setOnClickListener { trocarTela(PerfilActivity()) }

        binding.btnSalvar.setOnClickListener {
            val doadorModel = buscarDados()
            when (atualizarService.tryAtualizarDoador(doadorModel)) {
                0 -> { binding.tvMensagem.text = "Ocorreu um erro ao atualizar seu nome" }
                200 -> {
                    binding.tvMensagem.text = "Nome atualizado com sucesso"
                    val editor = prefs.edit()
                    editor.putString(DadosSharedSecret.USUARIO_NOME.toString(), binding.etNome.text.toString())
                    editor.apply()
                }
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

    private fun buscarDados(): DoadorModel {
        val nascimento = prefs.getString(DadosSharedSecret.USUARIO_NASCIMENTO.toString(), "NULL")

        return DoadorModel(
            id = prefs.getString(DadosSharedSecret.USUARIO_ID.toString(), ""),
            nome = binding.etNome.text.toString(),
            email = prefs.getString(DadosSharedSecret.USUARIO_EMAIL.toString(), ""),
            nascimento = if (nascimento === "NULL") LocalDate.parse(nascimento) else null,
            tipoSanguineo = prefs.getString(DadosSharedSecret.USUARIO_TIPO_SANGUINEO.toString(), "")
        )
    }
}