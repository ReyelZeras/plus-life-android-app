package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.pluslife.databinding.ActivityBuscarEnderecoBinding
import com.example.pluslife.databinding.ActivityHomeBinding
import com.example.pluslife.models.UsuarioEnderecoRequest
import com.example.pluslife.models.enum.DadosSharedSecret

class BuscarEnderecoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuscarEnderecoBinding
    lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_endereco)

        binding = ActivityBuscarEnderecoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = getSharedPreferences("DADOS", MODE_PRIVATE)

        val spinnerEstado : Spinner = binding.etEstado
        ArrayAdapter.createFromResource(
            this,
            R.array.estados,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerEstado.adapter = adapter
        }

        binding.btnBuscar.setOnClickListener {
            val cidade = binding.etCidade.text.toString()
            val estado = binding.etEstado.selectedItem.toString()
            val rua = binding.etRua.text.toString()
            val numero = binding.etNumero.text.toString().toInt()
            val bairro = binding.etBairro.text.toString()
            val endereco  = montarEndereco(bairro, rua, numero, cidade, estado)

            val novaTela = Intent(
                this,
                BancosProximosActivity::class.java
            )
            novaTela.putExtra("endereco", endereco)
            startActivity(novaTela)
        }

        navbar()

    }

    private fun montarEndereco(bairro: String, rua: String, numero: Int, cidade: String, estado: String): UsuarioEnderecoRequest {
        return UsuarioEnderecoRequest(
            email = prefs.getString(DadosSharedSecret.USUARIO_EMAIL.toString(), ""),
            bairro = bairro,
            rua = rua,
            numero = numero,
            cidade = cidade,
            estado = estado
        )
    }

    private fun navbar() {
        binding.navPerfil.setOnClickListener {
            val isLogado = prefs.getBoolean(DadosSharedSecret.USUARIO_LOGADO.toString(),false)

            if(isLogado){
                trocarTela(PerfilActivity())
            }else {
                trocarTela(LoginActivity())
            }
        }
        binding.navPontos.setOnClickListener {
            val isLogado = prefs.getBoolean(DadosSharedSecret.USUARIO_LOGADO.toString(),false)

            if(isLogado){
                trocarTela(BancosProximosActivity())
            }else {
                trocarTela(BuscarEnderecoActivity())
            }
        }
        binding.navHome.setOnClickListener { trocarTela(HomeActivity()) }
    }

    fun trocarTela(tela: Activity){
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }
}