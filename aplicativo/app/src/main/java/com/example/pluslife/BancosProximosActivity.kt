package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pluslife.databinding.ActivityBancosProximosBinding
import com.example.pluslife.models.BancoDeSangueEnderecoModel
import com.example.pluslife.models.UsuarioEnderecoRequest
import com.example.pluslife.models.enum.EnderecoSharedSecret.*
import com.example.pluslife.models.enum.UsuarioSharedSecret.*
import com.example.pluslife.rest.Rest
import com.example.pluslife.services.Banco
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class BancosProximosActivity : AppCompatActivity() {

    lateinit var binding: ActivityBancosProximosBinding
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBancosProximosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("DADOS", MODE_PRIVATE)

        navbar()

        val endereco = montarUsuarioEndereco()
        tryBuscarPontosProximos(endereco)

        binding.btnEditarEndereco.setOnClickListener { trocarTela(BuscarEnderecoActivity()) }
    }

    private fun configurarRecyclerView(pontos: List<BancoDeSangueEnderecoModel>?) {
        val recyclerContainer = binding.recyclerContainer
        recyclerContainer.layoutManager = LinearLayoutManager(baseContext)

        recyclerContainer.adapter = BancoDeSangueAdapter(pontos!!) {
            val telaPonto = Intent(
                this,
                BancoEspecificoActivity::class.java
            )
            val endereco = "${it.rua}, ${it.numero}, ${it.bairro}, ${it.cidade} - ${it.estado}"
            telaPonto.putExtra("nome", it.nome)
            telaPonto.putExtra("email", it.emailContato)
            telaPonto.putExtra("telefone", it.telefone)
            telaPonto.putExtra("endereco", endereco)
            telaPonto.putExtra("estoque", it.estoque as Serializable)
            startActivity(telaPonto)
        }
    }

    private fun tryBuscarPontosProximos(enderecoRequest: UsuarioEnderecoRequest) {
        val request = Rest.getInstance().create(Banco::class.java)

        request.getBancosProximos(enderecoRequest)
            .enqueue(object : Callback<List<BancoDeSangueEnderecoModel>> {
                override fun onResponse(
                    call: Call<List<BancoDeSangueEnderecoModel>>,
                    response: Response<List<BancoDeSangueEnderecoModel>>
                ) {
                    if (response.code() == 200) {
                        configurarMapa(response.body().orEmpty(), enderecoRequest)
                        configurarRecyclerView(response.body().orEmpty())
                    }
                }

                override fun onFailure(call: Call<List<BancoDeSangueEnderecoModel>>, t: Throwable) {
                    binding.tvPontosProximos.text = t.message
                }
            })
    }

    private fun configurarMapa(pontos: List<BancoDeSangueEnderecoModel>?, usuarioEndereco: UsuarioEnderecoRequest){

        binding.btnMapa.setOnClickListener {
            val novaTela = Intent(
                this,
                MapsActivity::class.java
            )
            novaTela.putExtra("pontos", pontos as Serializable)
            novaTela.putExtra("enderecoUsuario", usuarioEndereco)
            startActivity(novaTela)
        }

    }

    private fun montarUsuarioEndereco(): UsuarioEnderecoRequest {
        val isNovoEndereco = intent.getBooleanExtra("isNovoEndereco", false)

        val email = if (isNovoEndereco) null
            else prefs.getString(USUARIO_EMAIL.toString(), null)

        val rua = if (isNovoEndereco) prefs.getString(RUA.toString(), "")
            else prefs.getString(USUARIO_ENDERECO_RUA.toString(), "")

        val cidade = if (isNovoEndereco) prefs.getString(CIDADE.toString(), "")
            else prefs.getString(USUARIO_ENDERECO_CIDADE.toString(), "")

        val bairro = if (isNovoEndereco) prefs.getString(BAIRRO.toString(), "")
            else prefs.getString(USUARIO_ENDERECO_BAIRRO.toString(), "")

        val estado = if (isNovoEndereco) prefs.getString(ESTADO.toString(), "")
            else prefs.getString(USUARIO_ENDERECO_ESTADO.toString(), "")

        val numero = if (isNovoEndereco) prefs.getInt(NUMERO.toString(), 0)
            else prefs.getInt(USUARIO_ENDERECO_NUMERO.toString(), 0)

        if (!isNovoEndereco) {
            val editor = prefs.edit()
            editor.putString(RUA.toString(), rua)
            editor.putString(BAIRRO.toString(), bairro)
            editor.putString(CIDADE.toString(), cidade)
            editor.putString(ESTADO.toString(), estado)
            editor.putInt(NUMERO.toString(), numero)
            editor.apply()
        }

        return UsuarioEnderecoRequest(
            email, bairro, rua, numero, cidade, estado
        )
    }

    private fun navbar() {
        binding.navPerfil.setOnClickListener {
            val isLogado = prefs.getBoolean(USUARIO_LOGADO.toString(), false)

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
}


