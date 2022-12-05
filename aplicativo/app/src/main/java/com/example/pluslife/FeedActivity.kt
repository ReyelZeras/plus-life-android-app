package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pluslife.databinding.ActivityFeedBinding
import com.example.pluslife.models.PostagemModel
import com.example.pluslife.models.PostagemRequest
import com.example.pluslife.models.enum.UsuarioSharedSecret.*
import com.example.pluslife.rest.Rest
import com.example.pluslife.services.Postagem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedActivity : AppCompatActivity() {

    lateinit var binding: ActivityFeedBinding
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("DADOS", MODE_PRIVATE)

        navbar()
        tryBuscarPosts()

        binding.btnPostar.setOnClickListener {
            tryPublicar(binding.etPublicacao.text.toString())
        }
    }

    private fun tryPublicar(descricao: String) {
        val request = Rest.getInstance().create(Postagem::class.java)

        val novoPost = PostagemRequest(
            prefs.getString(USUARIO_EMAIL.toString(), null)!!,
            descricao
        )

        request.novaPostagem(novoPost).enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.code() == 201) {
                    telaSucesso()
                }

                if (response.code() == 204) {
                    telaErro("Erro ao apagar publicação.\nNO_CONTENT: status code 204")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                telaErro(t.message.toString())
            }
        })    }

    private fun tryBuscarPosts() {
        val request = Rest.getInstance().create(Postagem::class.java)

        request.getPostagens().enqueue(object : Callback<List<PostagemModel>> {
                override fun onResponse(
                    call: Call<List<PostagemModel>>,
                    response: Response<List<PostagemModel>>
                ) {
                    if (response.code() == 200) {
                        configurarRecyclerView(response.body().orEmpty())
                    }
                }

                override fun onFailure(call: Call<List<PostagemModel>>, t: Throwable) {
                    telaErro(t.message.toString())
                }
            })
    }

    private fun configurarRecyclerView(postagens: List<PostagemModel>?) {
        val idUsuario = prefs.getString(USUARIO_ID.toString(), "")
        val recyclerContainer = binding.recyclerContainer
        recyclerContainer.layoutManager = LinearLayoutManager(baseContext)

        recyclerContainer.adapter = PostagemAdapter(postagens!!, idUsuario!!) {
            tryExcluirConta(it.id)
        }
    }

    private fun tryExcluirConta(idPostagem: Long) {
        val request = Rest.getInstance().create(Postagem::class.java)

        request.deletePostagem(idPostagem).enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.code() == 200) {
                    telaSucesso()
                }

                if (response.code() == 204) {
                    telaErro("Erro ao apagar publicação.\nNO_CONTENT: status code 204")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                telaErro(t.message.toString())
            }
        })
    }

    private fun navbar() {
        binding.navHome.setOnClickListener { trocarTela(HomeActivity()) }
        binding.navPerfil.setOnClickListener { trocarTela(PerfilActivity())}
        binding.navPontos.setOnClickListener { trocarTela(BancosProximosActivity())}
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
    fun telaSucesso(){
        val novaTela = Intent(
            this,
            SucessoActivity::class.java
        )
        novaTela.putExtra("tela", "FEED")
        startActivity(novaTela)
    }

}