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

        telaLogin()
        //telaHome()
    }

    fun telaHome(){
        val telaHome = Intent(
            this,
            HomeActivity::class.java
        )
        startActivity(telaHome)
    }

    private fun telaLogin() {
        val telaLogin = Intent(
            this,
            LoginActivity::class.java
        )
        startActivity(telaLogin)
    }
}