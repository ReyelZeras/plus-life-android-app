package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityAtualizarEmailBinding
import com.example.pluslife.databinding.ActivityAtualizarNomeBinding
import com.example.pluslife.models.DoadorModel
import com.example.pluslife.models.enum.DadosSharedSecret
import com.example.pluslife.services.AtualizarService
import java.time.LocalDate

class AtualizarEmail : AppCompatActivity() {

    lateinit var binding: ActivityAtualizarEmailBinding
    lateinit var prefs: SharedPreferences
    lateinit var atualizarService: AtualizarService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atualizar_email)

        prefs = getSharedPreferences("DADOS", AppCompatActivity.MODE_PRIVATE)

        binding.btnVoltar.setOnClickListener { trocarTela(PerfilActivity()) }

        binding.btnSalvar.setOnClickListener {
            val doadorModel = buscarDados()
            when (atualizarService.tryAtualizarDoador(doadorModel)) {
                0 -> { binding.tvMensagem.text = "Ocorreu um erro ao atualizar seu e-mail" }
                200 -> {
                    binding.tvMensagem.text = "E-mail atualizado com sucesso"
                    val editor = prefs.edit()
                    editor.putString(DadosSharedSecret.USUARIO_EMAIL.toString(), binding.etEmail.text.toString())
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
            nome = prefs.getString(DadosSharedSecret.USUARIO_NOME.toString(), ""),
            email = prefs.getString(DadosSharedSecret.USUARIO_EMAIL.toString(), ""),
            nascimento = if (nascimento === "NULL") LocalDate.parse(nascimento) else null,
            tipoSanguineo = prefs.getString(DadosSharedSecret.USUARIO_TIPO_SANGUINEO.toString(), "")
        )
    }
}