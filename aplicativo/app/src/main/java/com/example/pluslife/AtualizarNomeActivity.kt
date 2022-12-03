package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityAtualizarNomeBinding
import com.example.pluslife.models.DoadorModel
import com.example.pluslife.models.enum.UsuarioSharedSecret
import com.example.pluslife.rest.Rest
import com.example.pluslife.services.AtualizarService
import com.example.pluslife.services.Doador
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class AtualizarNomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityAtualizarNomeBinding
    lateinit var prefs: SharedPreferences
    lateinit var atualizarService: AtualizarService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarNomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        atualizarService = AtualizarService()

        prefs = getSharedPreferences("DADOS", AppCompatActivity.MODE_PRIVATE)

        binding.btnVoltar.setOnClickListener { trocarTela(PerfilActivity()) }
        binding.btnSalvar.setOnClickListener { tryAtualizarDoador(buscarDados()) }

        binding.tvNomeAtual.text = prefs.getString(UsuarioSharedSecret.USUARIO_NOME.toString(), "--")
    }

    fun trocarTela(tela: Activity){
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }

    private fun buscarDados(): DoadorModel {
        val nascimento = prefs.getString(UsuarioSharedSecret.USUARIO_NASCIMENTO.toString(), "NULL")

        return DoadorModel(
            id = prefs.getString(UsuarioSharedSecret.USUARIO_ID.toString(), ""),
            nome = binding.etNome.text.toString(),
            email = prefs.getString(UsuarioSharedSecret.USUARIO_EMAIL.toString(), ""),
            nascimento = if (nascimento != "NULL") LocalDate.parse(nascimento) else null,
            tipoSanguineo = prefs.getString(UsuarioSharedSecret.USUARIO_TIPO_SANGUINEO.toString(), "")
        )
    }

    fun tryAtualizarDoador(doador: DoadorModel) {

        val request = Rest.getInstance().create(Doador::class.java)
        request.atualizar(doador).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                binding.tvMensagem.text = "Nome atualizado com sucesso"
                val editor = prefs.edit()
                editor.putString(UsuarioSharedSecret.USUARIO_NOME.toString(), binding.etNome.text.toString())
                editor.apply()

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                binding.tvMensagem.text = "Ocorreu um erro ao atualizar seu nome"
            }
        })
    }
}