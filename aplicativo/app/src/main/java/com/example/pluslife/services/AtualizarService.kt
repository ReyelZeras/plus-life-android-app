package com.example.pluslife.services

import androidx.fragment.app.Fragment
import com.example.pluslife.databinding.FragmentAtualizarNomeBinding
import com.example.pluslife.models.CadastroEnderecoRequest
import com.example.pluslife.models.DoadorModel
import com.example.pluslife.models.UsuarioEnderecoRequest
import com.example.pluslife.rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AtualizarService {

    fun tryAtualizarDoador(doador: DoadorModel): Int {

        val request = Rest.getInstance().create(Doador::class.java)
        var success = 0
        request.atualizar(doador).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                success = response.code()

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                success = 1
            }
        })

        return success
    }

    fun tryAtualizarEndereco(enderecoRequest: CadastroEnderecoRequest): Int {
        val request = Rest.getInstance().create(Usuario::class.java)
        var success = 0

        request.cadastroEndereco(enderecoRequest).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                success = response.code()

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                success = 0
            }
        })

        return success
    }
}