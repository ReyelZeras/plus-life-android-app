package com.example.pluslife

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityAtualizarEmailBinding
import com.example.pluslife.databinding.ActivityErroBinding
import com.example.pluslife.models.enum.MedidorNivel

class ErroActivity : AppCompatActivity() {

    lateinit var binding: ActivityErroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val novaTela = intent.getStringExtra("tela")
        val mensagem = intent.getStringExtra("mensagem")

        binding.btnSair.setOnClickListener{trocarTela(novaTela!!)}
        binding.tvErro.setText(mensagem)
    }

    fun trocarTela(novaTela: String){
        val activity: Activity
        when (novaTela) {
            "PERFIL" -> activity = PerfilActivity()
            "BANCOS_PROXIMOS" -> activity = BancosProximosActivity()
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