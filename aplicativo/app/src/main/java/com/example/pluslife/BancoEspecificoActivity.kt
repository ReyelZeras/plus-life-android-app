package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslife.databinding.ActivityBancoEspecificoBinding
import com.example.pluslife.models.NomeNivelSang
import com.example.pluslife.models.enum.EnderecoSharedSecret.*
import com.example.pluslife.models.enum.MedidorNivel
import com.example.pluslife.models.enum.UsuarioSharedSecret.*
import com.example.pluslife.models.enum.MedidorNivel.ATENCAO
import com.example.pluslife.models.enum.MedidorNivel.CRITICO
import com.example.pluslife.models.enum.MedidorNivel.ESTAVEL

class BancoEspecificoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBancoEspecificoBinding
    lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banco_especifico)

        binding = ActivityBancoEspecificoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("DADOS", MODE_PRIVATE)

        binding.btnVoltar.setOnClickListener {
            val novaTela = Intent(
                this,
                BancosProximosActivity::class.java
            )

            if (verificarSeEnderecoAtualIgualCadastro()){
                novaTela.putExtra("isNovoEndereco", false)
            } else {
                novaTela.putExtra("isNovoEndereco", true)
            }

            startActivity(novaTela)
        }

        navbar()

        val nome = intent.getStringExtra("nome")
        val email = intent.getStringExtra("email")
        val telefone = intent.getStringExtra("telefone")
        val endereco = intent.getStringExtra("endereco")
        val estoque: ArrayList<NomeNivelSang> =
            intent.getSerializableExtra("estoque") as ArrayList<NomeNivelSang>

        binding.tvNome.text = nome
        binding.tvEmail.text = email
        binding.tvTelefone.text = telefone
        binding.tvEndereco.text = endereco
        definirEstoque(estoque)
    }

    // verifica se o usuario esta utilizando o endereco cadastrado ou nao
    private fun verificarSeEnderecoAtualIgualCadastro(): Boolean {
        val ruaCadastro = prefs.getString(USUARIO_ENDERECO_RUA.toString(), "")
        val numeroCadastro = prefs.getInt(USUARIO_ENDERECO_NUMERO.toString(), 0)
        val ruaAtual = prefs.getString(RUA.toString(), "")
        val numeroAtual = prefs.getInt(NUMERO.toString(), 0)

        if (ruaCadastro == ruaAtual && numeroCadastro == numeroAtual) {
            return true
        }

        return false
    }

    private fun navbar() {
        binding.navHome.setOnClickListener { trocarTela(HomeActivity()) }
        binding.navPerfil.setOnClickListener { trocarTela(PerfilActivity()) }
        binding.navPontos.setOnClickListener { trocarTela(BancosProximosActivity()) }
    }


    fun trocarTela(tela: Activity) {
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }

    fun definirEstoque(listaEstoque: ArrayList<NomeNivelSang>) {
        println("VALORES ${listaEstoque.size}")
        println("ESTQ ${ESTAVEL.nivel}/${CRITICO.nivel}/${ATENCAO.nivel}")
        for (estoque in listaEstoque) {
            when (estoque.nome) {
                "A+" -> {
                    println("IF IF ${estoque.nivel}/A+")
                    if (estoque.nivel == ESTAVEL.nivel) binding.medidorAp.setImageResource(R.drawable.medidor_ap_estavel)
                    if (estoque.nivel == ATENCAO.nivel) binding.medidorAp.setImageResource(R.drawable.medidor_ap_atencao)
                    if (estoque.nivel == CRITICO.nivel) binding.medidorAp.setImageResource(R.drawable.medidor_ap_critico)
                }
                "A-" -> {
                    println("IF IF ${estoque.nivel}/A-")
                    if (estoque.nivel == ESTAVEL.nivel) binding.medidorAn.setImageResource(R.drawable.medidor_an_estavel)
                    if (estoque.nivel == ATENCAO.nivel) binding.medidorAn.setImageResource(R.drawable.medidor_an_atencao)
                    if (estoque.nivel == CRITICO.nivel) binding.medidorAn.setImageResource(R.drawable.medidor_an_critico)
                }
                "B+" -> {
                    println("IF IF ${estoque.nivel}/B+")
                    if (estoque.nivel.contentEquals(ESTAVEL.nivel)) binding.medidorBp.setImageResource(R.drawable.medidor_bp_estavel)
                    if (estoque.nivel == ATENCAO.nivel) binding.medidorBp.setImageResource(R.drawable.medidor_bp_atencao)
                    if (estoque.nivel == CRITICO.nivel) binding.medidorBp.setImageResource(R.drawable.medidor_bp_critico)
                }
                "B-" -> {
                    println("IF IF ${estoque.nivel}/B-")

                    if (estoque.nivel == ESTAVEL.nivel) binding.medidorBn.setImageResource(R.drawable.medidor_bn_estavel)
                    if (estoque.nivel == ATENCAO.nivel) binding.medidorBn.setImageResource(R.drawable.medidor_bn_atencao)
                    if (estoque.nivel == CRITICO.nivel) binding.medidorBn.setImageResource(R.drawable.medidor_bn_critico)
                }
                "AB+" -> {
                    println("IF IF ${estoque.nivel}/AB+")

                    if (estoque.nivel == ESTAVEL.nivel) binding.medidorAbp.setImageResource(R.drawable.medidor_abp_estavel)
                    if (estoque.nivel == ATENCAO.nivel) binding.medidorAbp.setImageResource(R.drawable.medidor_abp_atencao)
                    if (estoque.nivel == CRITICO.nivel) binding.medidorAbp.setImageResource(R.drawable.medidor_abp_critico)
                }
                "AB-" -> {
                    println("IF IF ${estoque.nivel}/AB-")

                    if (estoque.nivel == ESTAVEL.nivel) binding.medidorAbn.setImageResource(R.drawable.medidor_abn_estavel)
                    if (estoque.nivel == ATENCAO.nivel) binding.medidorAbn.setImageResource(R.drawable.medidor_abn_atencao)
                    if (estoque.nivel == CRITICO.nivel) binding.medidorAbn.setImageResource(R.drawable.medidor_abn_critico)
                }
                "O+" -> {
                    println("IF IF ${estoque.nivel}/O+")

                    if (estoque.nivel == ESTAVEL.nivel) binding.medidorOp.setImageResource(R.drawable.medidor_op_estavel)
                    if (estoque.nivel == ATENCAO.nivel) binding.medidorOp.setImageResource(R.drawable.medidor_op_atencao)
                    if (estoque.nivel == CRITICO.nivel) binding.medidorOp.setImageResource(R.drawable.medidor_op_critico)
                }
                "O-" -> {
                    println("IF IF ${estoque.nivel}/O-")

                    if (estoque.nivel == ESTAVEL.nivel) binding.medidorOn.setImageResource(R.drawable.medidor_on_estavel)
                    if (estoque.nivel == ATENCAO.nivel) binding.medidorOn.setImageResource(R.drawable.medidor_on_atencao)
                    if (estoque.nivel == CRITICO.nivel) binding.medidorOn.setImageResource(R.drawable.medidor_on_critico)
                }
                else -> {
                    binding.tvNome.text = "deu ruim"
                }
            }
        }
    }
}