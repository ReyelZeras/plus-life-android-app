package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.pluslife.databinding.ActivityAtualizarEnderecoBinding
import com.example.pluslife.models.CadastroEnderecoRequest
import com.example.pluslife.models.enum.UsuarioSharedSecret.*
import com.example.pluslife.rest.Rest
import com.example.pluslife.services.AtualizarService
import com.example.pluslife.services.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

            tryAtualizarEndereco(endereco)
        }
    }

    private fun montarEnderecoAtual(): String {
        val rua = prefs.getString(USUARIO_ENDERECO_RUA.toString(), "Rua Haddok Lobo")
        val numero = prefs.getInt(USUARIO_ENDERECO_NUMERO.toString(), 353)
        val bairro = prefs.getString(USUARIO_ENDERECO_BAIRRO.toString(), "Consolação")
        val cidade = prefs.getString(USUARIO_ENDERECO_CIDADE.toString(), "São Paulo")
        val estado = prefs.getString(USUARIO_ENDERECO_ESTADO.toString(), "SP")
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
        editor.putString(USUARIO_ENDERECO_BAIRRO.toString(), bairro)
        editor.putString(USUARIO_ENDERECO_RUA.toString(), rua)
        editor.putInt(USUARIO_ENDERECO_NUMERO.toString(), numero)
        editor.putString(USUARIO_ENDERECO_CIDADE.toString(), cidade)
        editor.putString(USUARIO_ENDERECO_ESTADO.toString(), estado)
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
            email = prefs.getString(USUARIO_EMAIL.toString(), "")!!,
            bairro = bairro,
            rua = rua,
            numero = numero,
            cidade = cidade,
            estado = estado
    )

    fun tryAtualizarEndereco(enderecoRequest: CadastroEnderecoRequest, ) {
        val request = Rest.getInstance().create(Usuario::class.java)

        request.cadastroEndereco(enderecoRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.code() == 201){
                    binding.tvMensagem.text = "Endereço atualizado com sucesso"
                    atualizarSharedPreferences(
                        enderecoRequest.bairro,
                        enderecoRequest.rua,
                        enderecoRequest.numero,
                        enderecoRequest.cidade,
                        enderecoRequest.estado
                    )
                }else {
                    binding.tvMensagem.text = "Caio hideki ${response}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                binding.tvMensagem.text = "Ocorreu um erro ao atualizar seu endereço"
            }
        })
    }
}