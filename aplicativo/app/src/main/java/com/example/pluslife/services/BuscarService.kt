package com.example.pluslife.services

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.pluslife.MainActivity
import com.example.pluslife.models.DoadorModel
import com.example.pluslife.models.DoadorResponse
import com.example.pluslife.models.enum.DadosSharedSecret
import com.example.pluslife.rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuscarService {

    lateinit var prefs: SharedPreferences

    fun buscarDoador(idDoador: String) {
        val request = Rest.getInstance().create(Doador::class.java)

        request.getDoador(idDoador).enqueue(object : Callback<DoadorResponse> {
            override fun onResponse(call: Call<DoadorResponse>, response: Response<DoadorResponse>) {
                DoadorModel(
                    id = idDoador,
                    nome = response.body()!!.nome,
                    email = response.body()!!.email,
                    nascimento = response.body()!!.dataNascimento,
                    tipoSanguineo = response.body()!!.tipoSanguineo
                )


                val editor = prefs.edit()
                editor.putString(DadosSharedSecret.USUARIO_NASCIMENTO.toString(), response.body()?.dataNascimento.toString())
                editor.putString(DadosSharedSecret.USUARIO_TIPO_SANGUINEO.toString(), response.body()?.tipoSanguineo.toString())
                editor.apply()
            }

            override fun onFailure(call: Call<DoadorResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}