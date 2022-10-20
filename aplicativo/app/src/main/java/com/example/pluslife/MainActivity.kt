package com.example.pluslife

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.pluslife.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("DADOS", MODE_PRIVATE)

        varificaLogin(prefs)
        navbar()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentToolbar.id, ToolbarFragment())
        transaction.commit()

    }

    private fun navbar() {
        binding.navHome.setOnClickListener { trocarTela(HomeFragment()) }
        binding.navPerfil.setOnClickListener { trocarTela(QuemPodeDoarFragment()) }
        binding.navPontos.setOnClickListener { trocarTela(ComoDoarFragment()) }
    }

    private fun varificaLogin(prefs: SharedPreferences) {
        if (prefs.getBoolean("LOGADO", true)) {
            trocarTela(HomeFragment())
        } else {
            telaLogin()
        }
    }

    fun trocarTela(tela: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentTela.id, tela)
        transaction.commit()
    }

    private fun telaLogin() {
        val telaLogin = Intent(
            this,
            LoginActivity::class.java
        )
        startActivity(telaLogin)
    }
}