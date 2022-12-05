package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityMainBinding
import com.example.pluslife.models.enum.UsuarioSharedSecret.USUARIO_LOGADO

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("DADOS", MODE_PRIVATE)

        varificaLogin(prefs)

    }

    private fun varificaLogin(prefs: SharedPreferences) {
        if (prefs.getBoolean(USUARIO_LOGADO.toString(), false)) {
            //trocarTela(HomeFragment())
            trocarTela(HomeActivity())
        } else {
            trocarTela(LoginActivity())
        }
    }

    fun trocarTela(tela: Activity) {
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }

    fun telaPerfil() {
        val novaTela = Intent(
            this,
            PerfilActivity::class.java
        )
        startActivity(novaTela)
    }


}