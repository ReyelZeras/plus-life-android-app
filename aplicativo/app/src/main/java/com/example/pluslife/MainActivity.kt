package com.example.pluslife

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

        binding.btnLogin.setOnClickListener{
            tryLogin()}
    }

    private fun tryLogin(){
        val email = binding.etEmail.text.toString()
        val senha = binding.etSenha.text.toString()
        val body = LoginRequest(email, senha)

        val request = Rest.getInstance().create(Usuario::class.java)

        request.login(body).enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                response.body().toString()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}