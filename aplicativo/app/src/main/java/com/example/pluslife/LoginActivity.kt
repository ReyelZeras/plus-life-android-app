package com.example.pluslife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityLoginBinding
import com.example.pluslife.models.DoadorResponse
import com.example.pluslife.models.EnderecoModel
import com.example.pluslife.models.UsuarioModel
import com.example.pluslife.models.enum.UsuarioSharedSecret.*
import com.example.pluslife.rest.Rest
import com.example.pluslife.services.Doador
import com.example.pluslife.services.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { tryLogin() }
        binding.tvCadastrar.setOnClickListener { telaCadastro() }
        binding.tvVoltar.setOnClickListener { telaHome() }
    }

    private fun tryLogin() {
        val email = binding.etEmail.text.toString()
        val senha = binding.etSenha.text.toString()

        val request = Rest.getInstance().create(Usuario::class.java)

        request.login(email, senha).enqueue(object : Callback<UsuarioModel> {
            override fun onResponse(call: Call<UsuarioModel>, response: Response<UsuarioModel>) {
                //binding.tvMensagem.setText(response.body().toString())

                if (response.code() == 200) {
                    tryGetEndereco(response.body()!!.id)
                    tryGetDoador(response.body()!!.id)
                    telaHome()
                }

                if (response.code() == 204) {
                    binding.tvMensagem.setText("E-mail ou senha inválido")
                }
            }

            override fun onFailure(call: Call<UsuarioModel>, t: Throwable) {
                binding.tvMensagem.setText(t.message)
            }
        })
    }

    private fun tryGetEndereco(idUsuario: Int) {
        val request = Rest.getInstance().create(Usuario::class.java)

        request.getEndereco(idUsuario).enqueue(object : Callback<EnderecoModel> {
            override fun onResponse(call: Call<EnderecoModel>, response: Response<EnderecoModel>) {

                if (response.code() == 200) {
                    val prefs = getSharedPreferences("DADOS", MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putString(USUARIO_ENDERECO_RUA.toString(), response.body()?.rua)
                    editor.putString(USUARIO_ENDERECO_BAIRRO.toString(), response.body()?.bairro)
                    editor.putString(USUARIO_ENDERECO_CIDADE.toString(), response.body()?.cidade)
                    editor.putString(USUARIO_ENDERECO_ESTADO.toString(), response.body()?.estado)
                    editor.putInt(USUARIO_ENDERECO_NUMERO.toString(), response.body()?.numero!!)
                    editor.putBoolean(USUARIO_LOGADO.toString(), true)
                    editor.apply()
                }
            }

            override fun onFailure(call: Call<EnderecoModel>, t: Throwable) {
                binding.tvMensagem.setText(t.message)
            }
        })
    }

    private fun tryGetDoador(idUsuario: Int) {
        val request = Rest.getInstance().create(Doador::class.java)

        request.getDoador(idUsuario.toString()).enqueue(object : Callback<DoadorResponse> {
            override fun onResponse(call: Call<DoadorResponse>, response: Response<DoadorResponse>) {

                if (response.code() == 200) {
                    val prefs = getSharedPreferences("DADOS", MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putString(USUARIO_ID.toString(), idUsuario.toString())
                    editor.putString(USUARIO_NOME.toString(), response.body()?.nome)
                    editor.putString(USUARIO_EMAIL.toString(), response.body()?.email)
                    editor.putString(USUARIO_TIPO_SANGUINEO.toString(), response.body()?.tipoSanguineo)
                    editor.putBoolean(USUARIO_LOGADO.toString(), true)
                    editor.apply()
                }
            }

            override fun onFailure(call: Call<DoadorResponse>, t: Throwable) {
                binding.tvMensagem.setText(t.message)
            }
        })
    }

    fun telaCadastro() {
        val telaCadastro = Intent(
            this,
            CadastroActivity::class.java
        )
        startActivity(telaCadastro)
    }

    fun telaHome() {
        val telaHome = Intent(
            this,
            HomeActivity::class.java
        )
        startActivity(telaHome)
    }
}