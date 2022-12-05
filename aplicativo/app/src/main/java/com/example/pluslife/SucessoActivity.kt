package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityBancosProximosBinding
import com.example.pluslife.databinding.ActivityErroBinding
import com.example.pluslife.databinding.ActivitySucessoBinding

class SucessoActivity : AppCompatActivity() {

    lateinit var binding: ActivitySucessoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySucessoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val novaTela = intent.getStringExtra("tela")
        binding.btnSair.setOnClickListener{trocarTela(novaTela!!)}
    }



    fun trocarTela(novaTela: String){
        val activity: Activity
        when (novaTela) {
            "PERFIL" -> activity = PerfilActivity()
            "BANCOS_PROXIMOS" -> activity = BancosProximosActivity()
            "FEED" -> activity = FeedActivity()
            else -> activity = HomeActivity()
        }

        startActivity(
            Intent(
                this,
                activity::class.java
            )
        )
    }


}