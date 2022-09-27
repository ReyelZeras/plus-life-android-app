package com.example.pluslife.services

import com.example.pluslife.models.CadastroDoadorRequest
import com.example.pluslife.models.CadastroEnderecoRequest
import com.example.pluslife.models.LoginRequest
import com.example.pluslife.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Usuario {

    @POST("/usuario/login")
    fun login(@Body body: LoginRequest): Call<LoginResponse>

    @POST("/usuario/doador")
    fun cadastroDoador(@Body body: CadastroDoadorRequest): Call<String>

    @POST("/usuario/endereco")
    fun cadastroEndereco(@Body body: CadastroEnderecoRequest): Call<String>
}