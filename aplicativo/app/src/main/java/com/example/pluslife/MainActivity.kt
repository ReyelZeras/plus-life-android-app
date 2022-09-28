package com.example.pluslife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityMainBinding
import com.example.pluslife.models.LoginRequest
import com.example.pluslife.models.LoginResponse
import com.example.pluslife.rest.Rest
import com.example.pluslife.services.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener{ tryLogin() }
        binding.tvCadastrar.setOnClickListener { telaCadastro() }
    }

    private fun tryLogin(){
        val email = binding.etEmail.text.toString()
        val senha = binding.etSenha.text.toString()

        val request = Rest.getInstance().create(Usuario::class.java)

        request.login(email, senha).enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                binding.teste.setText(response.body().toString())
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                binding.teste.setText(t.message)
            }
        })
    }

    fun telaCadastro(){
        val telaCadastro = Intent(
            this,
            CadastroActivity::class.java
        )
        startActivity(telaCadastro)
    }
}