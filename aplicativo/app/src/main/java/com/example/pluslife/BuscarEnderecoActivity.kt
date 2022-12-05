package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.pluslife.databinding.ActivityBuscarEnderecoBinding
import com.example.pluslife.models.UsuarioEnderecoRequest
import com.example.pluslife.models.enum.EnderecoSharedSecret.*
import com.example.pluslife.models.enum.UsuarioSharedSecret

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

            val novaTela = Intent(
                this,
                BancosProximosActivity::class.java
            )
            val editor = prefs.edit()
            editor.putString(RUA.toString(), rua)
            editor.putString(BAIRRO.toString(), bairro)
            editor.putString(CIDADE.toString(), cidade)
            editor.putString(ESTADO.toString(), estado)
            editor.putInt(NUMERO.toString(), numero)
            editor.apply()

            novaTela.putExtra("isNovoEndereco", true)
            startActivity(novaTela)
        }

        navbar()

    }

    private fun montarEndereco(bairro: String, rua: String, numero: Int, cidade: String, estado: String): UsuarioEnderecoRequest {
        return UsuarioEnderecoRequest(
            email = prefs.getString(UsuarioSharedSecret.USUARIO_EMAIL.toString(), ""),
            bairro = bairro,
            rua = rua,
            numero = numero,
            cidade = cidade,
            estado = estado
        )
    }

    private fun navbar() {
        binding.navHome.setOnClickListener { trocarTela(HomeActivity()) }
        binding.navPerfil.setOnClickListener { trocarTela(PerfilActivity()) }
        binding.navPontos.setOnClickListener { trocarTela(BancosProximosActivity()) }
    }


    fun trocarTela(tela: Activity){
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }
}