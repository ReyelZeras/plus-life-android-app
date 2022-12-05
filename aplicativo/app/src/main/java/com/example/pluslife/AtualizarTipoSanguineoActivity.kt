package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.pluslife.databinding.ActivityAtualizarTipoSanguineoBinding
import com.example.pluslife.models.DoadorModel
import com.example.pluslife.models.enum.UsuarioSharedSecret
import com.example.pluslife.rest.Rest
import com.example.pluslife.services.AtualizarService
import com.example.pluslife.services.Doador
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class AtualizarTipoSanguineoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarTipoSanguineoBinding
    lateinit var prefs: SharedPreferences
    lateinit var atualizarService: AtualizarService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarTipoSanguineoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("DADOS", AppCompatActivity.MODE_PRIVATE)

        binding.btnVoltar.setOnClickListener { trocarTela(PerfilActivity()) }
        binding.btnSalvar.setOnClickListener { tryAtualizarDoador(buscarDados()) }

        binding.tvTipoSang.text = prefs.getString(UsuarioSharedSecret.USUARIO_TIPO_SANGUINEO.toString(), "--")

        val spinner: Spinner = binding.spTipoSanguineo
        ArrayAdapter.createFromResource(
            this,
            R.array.tipos_sanguineos,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    fun trocarTela(tela: Activity){
        val novaTela = Intent(
            this,
            tela::class.java
        )
        startActivity(novaTela)
    }

    private fun buscarDados(): DoadorModel {
        val nascimento = prefs.getString(UsuarioSharedSecret.USUARIO_NASCIMENTO.toString(), "NULL")

        return DoadorModel(
            id = prefs.getString(UsuarioSharedSecret.USUARIO_ID.toString(), ""),
            nome = prefs.getString(UsuarioSharedSecret.USUARIO_NOME.toString(), ""),
            email = prefs.getString(UsuarioSharedSecret.USUARIO_EMAIL.toString(), ""),
            nascimento = if (nascimento != "NULL") LocalDate.parse(nascimento) else null,
            tipoSanguineo = binding.spTipoSanguineo.selectedItem.toString()
        )
    }

    fun tryAtualizarDoador(doador: DoadorModel) {

        val request = Rest.getInstance().create(Doador::class.java)
        request.atualizar(doador).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                telaSucesso()
                val editor = prefs.edit()
                editor.putString(UsuarioSharedSecret.USUARIO_TIPO_SANGUINEO.toString(), binding.spTipoSanguineo.selectedItem.toString())
                editor.apply()

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                telaErro("Ocorreu um erro ao atualizar seu tipo sanguíneo")
            }
        })
    }

    fun telaErro(mensagem: String){
        val novaTela = Intent(
            this,
            ErroActivity::class.java
        )
        novaTela.putExtra("tela", "PERFIL")
        novaTela.putExtra("mensagem", mensagem)
        startActivity(novaTela)
    }

    fun telaSucesso(){
        val novaTela = Intent(
            this,
            SucessoActivity::class.java
        )
        novaTela.putExtra("tela", "PERFIL")
        startActivity(novaTela)
    }
}