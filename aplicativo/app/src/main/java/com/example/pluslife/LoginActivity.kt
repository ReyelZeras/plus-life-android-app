package com.example.pluslife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityLoginBinding
import com.example.pluslife.models.LoginResponse
import com.example.pluslife.models.enum.DadosSharedSecret.USUARIO_EMAIL
import com.example.pluslife.models.enum.DadosSharedSecret.USUARIO_ID
import com.example.pluslife.models.enum.DadosSharedSecret.USUARIO_LOGADO
import com.example.pluslife.models.enum.DadosSharedSecret.USUARIO_NASCIMENTO
import com.example.pluslife.models.enum.DadosSharedSecret.USUARIO_NOME
import com.example.pluslife.models.enum.DadosSharedSecret.USUARIO_TIPO_SANGUINEO
import com.example.pluslife.rest.Rest
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
    }

    private fun tryLogin() {
        val email = binding.etEmail.text.toString()
        val senha = binding.etSenha.text.toString()

        val request = Rest.getInstance().create(Usuario::class.java)

        request.login(email, senha).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                //binding.tvMensagem.setText(response.body().toString())

                if (response.code() == 200) {
                    val prefs = getSharedPreferences("DADOS", MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putString(USUARIO_ID.toString(), response.body()?.id.toString())
                    editor.putString(USUARIO_NOME.toString(), response.body()?.nome.toString())
                    editor.putString(USUARIO_EMAIL.toString(), response.body()?.email.toString())
                    editor.putBoolean(USUARIO_LOGADO.toString(), true)
                    editor.apply()
                    telaHome()
                }

                if (response.code() == 204) {
                    binding.tvMensagem.setText("E-mail ou senha inválido")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
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
            MainActivity::class.java
        )
        startActivity(telaHome)
    }
}