package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityPerfilBinding
import com.example.pluslife.models.enum.UsuarioSharedSecret
import com.example.pluslife.models.enum.UsuarioSharedSecret.*
import com.example.pluslife.rest.Rest
import com.example.pluslife.services.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        binding.btnSair.setOnClickListener { logout() }

        binding.btnApagarConta.setOnClickListener { excluirConta() }
    }

    private fun logout() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
        trocarTela(HomeActivity())
    }

    private fun excluirConta() {
        val request = Rest.getInstance().create(Usuario::class.java)
        val id = prefs.getInt(USUARIO_ID.toString(), 0)

        //verifica se o id está em prefs ou não, pois se n estiver será 0
        if (id != 0) {
            request.excluir(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        binding.tvMensagem.text = "Conta excluida com sucesso!"
                        logout()
                    }

                    if (response.code() == 200) {
                        binding.tvMensagem.text = "Falha ao excluir conta: ID de Usuário não encontrado"
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    binding.tvMensagem.text = "Ocorreu um erro ao excluir sua conta"
                }
            })
        }
    }

    private fun iniciarLabels() {
        binding.tvNomeAtual.text = prefs.getString(USUARIO_NOME.toString(), "Reyel Zapateiro Magalhães 2")
        binding.tvEmailAtual.text = prefs.getString(USUARIO_EMAIL.toString(), "email@gmail.com")
        binding.tvTipoSangAtual.text = prefs.getString(USUARIO_TIPO_SANGUINEO.toString(), "AB-")
        binding.tvEnderecoAtual.text = montarEnderecoAtual()
    }
    private fun montarEnderecoAtual(): String {
        val rua = prefs.getString(USUARIO_ENDERECO_RUA.toString(), "Rua Haddok Lobo")
        val numero = prefs.getInt(USUARIO_ENDERECO_NUMERO.toString(), 353)
        val bairro = prefs.getString(USUARIO_ENDERECO_BAIRRO.toString(), "Consolação")
        val cidade = prefs.getString(USUARIO_ENDERECO_CIDADE.toString(), "São Paulo")
        val estado = prefs.getString(USUARIO_ENDERECO_ESTADO.toString(), "SP")
        return "$rua, $numero, $bairro, $cidade - $estado"
    }


    private fun navbar() {
        binding.navHome.setOnClickListener { trocarTela(HomeActivity()) }

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