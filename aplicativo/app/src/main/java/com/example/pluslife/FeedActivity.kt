package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityBancosProximosBinding
import com.example.pluslife.databinding.ActivityFeedBinding
import com.example.pluslife.models.enum.UsuarioSharedSecret

class FeedActivity : AppCompatActivity() {

    lateinit var binding: ActivityFeedBinding
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navbar()
    }


    private fun navbar() {
        binding.navPerfil.setOnClickListener {
            val isLogado = prefs.getBoolean(UsuarioSharedSecret.USUARIO_LOGADO.toString(), false)

            if (isLogado) {
                trocarTela(PerfilActivity())
            } else {
                trocarTela(LoginActivity())
            }
        }
        binding.navHome.setOnClickListener { trocarTela(HomeActivity()) }
    }


    fun trocarTela(tela: Activity) {
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }

    fun telaErro(mensagem: String){
        val novaTela = Intent(
            this,
            ErroActivity::class.java
        )
        novaTela.putExtra("tela", "HOME")
        novaTela.putExtra("mensagem", mensagem)
        startActivity(novaTela)
    }
}