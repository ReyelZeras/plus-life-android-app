package com.example.pluslife

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.pluslife.databinding.ActivityCadastroBinding
import com.example.pluslife.databinding.ActivityMainBinding
import com.example.pluslife.models.CadastroDoadorRequest
import com.example.pluslife.models.CadastroEnderecoRequest
import com.example.pluslife.models.LoginRequest
import com.example.pluslife.models.LoginResponse
import com.example.pluslife.rest.Rest
import com.example.pluslife.services.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCadastrar.setOnClickListener{
            tryCadastro()}

        val spinnerEstado : Spinner = binding.etEstado
        ArrayAdapter.createFromResource(
            this,
            R.array.estados,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerEstado.adapter = adapter
        }

        val spinner: Spinner = binding.tipoSanguineo
        ArrayAdapter.createFromResource(
            this,
            R.array.tipos_sanguineos,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun tryCadastro(){
        val nome = binding.etNome.text.toString()
        val email = binding.etEmail.text.toString()
        val senha = binding.etSenha.text.toString()
        val tipoSanguineo = binding.tipoSanguineo.selectedItem.toString()
        val nascimento = binding.etNascimento.text.toString()

        val body = CadastroDoadorRequest( nome, email, senha, nascimento, tipoSanguineo)

        val request = Rest.getInstance().create(Usuario::class.java)

        request.cadastroDoador(body).enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                binding.mensagem.setText("User:" + response.toString())
                tryCadastroEndereco(request)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                binding.mensagem.setText("Erro:" + t.message)
            }
        })
    }

    private fun tryCadastroEndereco(request: Usuario) {
        val email = binding.etEmail.text.toString()
        val cidade = binding.etCidade.text.toString()
        val estado = binding.etEstado.selectedItem.toString()
        val rua = binding.etRua.text.toString()
        val numero = binding.etNumero.text.toString().toInt()
        val bairro = binding.etBairro.text.toString()

        val body = CadastroEnderecoRequest(email, bairro, rua, numero, cidade, estado)

        request.cadastroEndereco(body).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                binding.mensagem.setText("\nCadastro:" + response.toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                binding.mensagem.setText("\nCad Erro:" + t.message)
            }
        })
    }
}