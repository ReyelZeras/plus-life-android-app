package com.example.pluslife.services

import com.example.pluslife.models.BancoDeSangueEnderecoModel
import com.example.pluslife.models.UsuarioEnderecoRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Banco {

    @POST("banco/proximos")
    fun getBancosProximos(@Body enderecoRequest: UsuarioEnderecoRequest): Call<List<BancoDeSangueEnderecoModel>>
}