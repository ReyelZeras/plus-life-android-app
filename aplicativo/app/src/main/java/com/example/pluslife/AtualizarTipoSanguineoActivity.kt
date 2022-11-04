package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.pluslife.databinding.ActivityAtualizarNomeBinding
import com.example.pluslife.databinding.ActivityAtualizarTipoSanguineoBinding
import com.example.pluslife.databinding.ActivityCadastroBinding
import com.example.pluslife.models.DoadorModel
import com.example.pluslife.models.enum.DadosSharedSecret
import com.example.pluslife.services.AtualizarService
import java.time.LocalDate

class AtualizarTipoSanguineoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarTipoSanguineoBinding
    lateinit var prefs: SharedPreferences
    lateinit var atualizarService: AtualizarService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarTipoSanguineoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("DADOS", AppCompatActivity.MODE_PRIVATE)

        binding.btnVoltar.setOnClickListener { trocarTela(PerfilActivity()) }

        binding.btnSalvar.setOnClickListener {
            val doadorModel = buscarDados()
            when (atualizarService.tryAtualizarDoador(doadorModel)) {
                0 -> { binding.tvMensagem.text = "Ocorreu um erro ao atualizar seu tipo sanguíneo" }
                200 -> {
                    binding.tvMensagem.text = "Tipo sanguíneo atualizado com sucesso"
                    val editor = prefs.edit()
                    editor.putString(DadosSharedSecret.USUARIO_TIPO_SANGUINEO.toString(), binding.spTipoSanguineo.selectedItem.toString())
                    editor.apply()
                }
            }
        }

        binding.btnSalvar.setOnClickListener{}


        val spinner: Spinner = binding.spTipoSanguineo
        ArrayAdapter.createFromResource(
            this,
            R.array.tipos_sanguineos,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
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