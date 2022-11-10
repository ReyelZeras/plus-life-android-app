package com.example.pluslife

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pluslife.databinding.ActivityBancosProximosBinding
import com.example.pluslife.models.BancoDeSangueEnderecoModel
import com.example.pluslife.models.DoadorModel
import com.example.pluslife.models.DoadorResponse
import com.example.pluslife.models.UsuarioEnderecoRequest
import com.example.pluslife.models.enum.DadosSharedSecret.*
import com.example.pluslife.rest.Rest
import com.example.pluslife.services.Banco
import com.example.pluslife.services.Doador
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BancosProximosActivity : AppCompatActivity() {

    lateinit var binding: ActivityBancosProximosBinding
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBancosProximosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("DADOS", MODE_PRIVATE)

        val endereco = montarUsuarioEndereco()
        tryBuscarPontosProximos(endereco)
    }

    private fun configurarRecyclerView(pontos: List<BancoDeSangueEnderecoModel>?) {
        val recyclerContainer = binding.recyclerContainer
        recyclerContainer.layoutManager = LinearLayoutManager(baseContext)

        recyclerContainer.adapter = BancoDeSangueAdapter(pontos!!) {
            var telaPonto = Intent(
                this,
                ComoDoarActivity::class.java
            )
            startActivity(telaPonto)
        }
    }

    private fun tryBuscarPontosProximos(enderecoRequest: UsuarioEnderecoRequest) {
        val request = Rest.getInstance().create(Banco::class.java)

        request.getBancosProximos(enderecoRequest).enqueue(object : Callback<List<BancoDeSangueEnderecoModel>> {
            override fun onResponse(call: Call<List<BancoDeSangueEnderecoModel>>, response: Response<List<BancoDeSangueEnderecoModel>>) {
                if (response.code() == 200) {
                    configurarRecyclerView(response.body().orEmpty())
                }
            }

            override fun onFailure(call: Call<List<BancoDeSangueEnderecoModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun montarUsuarioEndereco(): UsuarioEnderecoRequest {
        return UsuarioEnderecoRequest(
            email = prefs.getString(USUARIO_EMAIL.toString(), ""),
            bairro = prefs.getString(ENDERECO_BAIRRO.toString(), ""),
            rua = prefs.getString(ENDERECO_RUA.toString(), ""),
            numero = prefs.getInt(ENDERECO_NUMERO.toString(), 0),
            cidade = prefs.getString(ENDERECO_CIDADE.toString(), ""),
            estado = prefs.getString(ENDERECO_ESTADO.toString(), "")
        )
    }
}