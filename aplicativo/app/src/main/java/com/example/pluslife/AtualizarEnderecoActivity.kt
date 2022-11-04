package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.pluslife.databinding.ActivityAtualizarEnderecoBinding
import com.example.pluslife.databinding.ActivityCadastroBinding
import com.example.pluslife.models.CadastroEnderecoRequest
import com.example.pluslife.models.DoadorModel
import com.example.pluslife.models.UsuarioEnderecoRequest
import com.example.pluslife.models.enum.DadosSharedSecret.*
import com.example.pluslife.services.AtualizarService
import java.time.LocalDate

class AtualizarEnderecoActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAtualizarEnderecoBinding
    lateinit var prefs: SharedPreferences
    lateinit var atualizarService: AtualizarService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarEnderecoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("DADOS", AppCompatActivity.MODE_PRIVATE)

        val spinnerEstado : Spinner = binding.etEstado
        ArrayAdapter.createFromResource(
            this,
            R.array.estados,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerEstado.adapter = adapter
        }

        binding.btnVoltar.setOnClickListener { trocarTela(PerfilActivity()) }

        binding.tvEnderecoAtual.text = montarEnderecoAtual()

        binding.btnSalvar.setOnClickListener {
            val cidade = binding.etCidade.text.toString()
            val estado = binding.etEstado.selectedItem.toString()
            val rua = binding.etRua.text.toString()
            val numero = binding.etNumero.text.toString().toInt()
            val bairro = binding.etBairro.text.toString()
            val endereco = montarEndereco(bairro, rua, numero, cidade, estado)

            when (atualizarService.tryAtualizarEndereco(endereco)) {
                0 -> { binding.tvMensagem.text = "Ocorreu um erro ao atualizar seu endereço" }
                200 -> {
                    binding.tvMensagem.text = "Endereço atualizado com sucesso"
                    atualizarSharedPreferences(bairro, rua, numero, cidade, estado)
                }
            }
        }
    }

    private fun montarEnderecoAtual(): String {
        val rua = prefs.getString(ENDERECO_RUA.toString(), "Rua Haddok Lobo")
        val numero = prefs.getInt(ENDERECO_NUMERO.toString(), 353)
        val bairro = prefs.getString(ENDERECO_BAIRRO.toString(), "Consolação")
        val cidade = prefs.getString(ENDERECO_CIDADE.toString(), "São Paulo")
        val estado = prefs.getString(ENDERECO_ESTADO.toString(), "SP")
        return "$rua, $numero, $bairro, $cidade - $estado"
    }

    private fun atualizarSharedPreferences(
        bairro: String,
        rua: String,
        numero: Int,
        cidade: String,
        estado: String
    ) {
        val editor = prefs.edit()
        editor.putString(ENDERECO_BAIRRO.toString(), bairro)
        editor.putString(ENDERECO_RUA.toString(), rua)
        editor.putInt(ENDERECO_NUMERO.toString(), numero)
        editor.putString(ENDERECO_CIDADE.toString(), cidade)
        editor.putString(ENDERECO_ESTADO.toString(), estado)
        editor.apply()
    }


    fun trocarTela(tela: Activity){
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }

    private fun montarEndereco(
        bairro: String,
        rua: String,
        numero: Int,
        cidade: String,
        estado: String
    ) = CadastroEnderecoRequest(
            email = prefs.getString(USUARIO_NOME.toString(), "")!!,
            bairro = bairro,
            rua = rua,
            numero = numero,
            cidade = cidade,
            estado = estado
    )
}