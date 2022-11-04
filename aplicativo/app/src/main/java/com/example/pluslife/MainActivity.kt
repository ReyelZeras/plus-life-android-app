package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.pluslife.databinding.ActivityMainBinding
import com.example.pluslife.models.enum.DadosSharedSecret.USUARIO_LOGADO

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("DADOS", MODE_PRIVATE)

        varificaLogin(prefs)
//        navbar()
//
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(binding.fragmentToolbar.id, ToolbarFragment())
//        transaction.commit()

    }

//    private fun navbar() {
//        binding.navHome.setOnClickListener { trocarTela(HomeFragment()) }
//        binding.navPerfil.setOnClickListener { trocarTela(PerfilFragment()) }
//        binding.navPontos.setOnClickListener { trocarTela(ComoDoarFragment()) }
//    }

    private fun varificaLogin(prefs: SharedPreferences) {
        if (prefs.getBoolean(USUARIO_LOGADO.toString(), true)) {
            //trocarTela(HomeFragment())
            trocarTela(HomeActivity())
        } else {
            trocarTela(LoginActivity())
        }
    }

//    fun trocarTela(tela: Fragment){
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(binding.fragmentTela.id, tela)
//        transaction.commit()
//    }

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