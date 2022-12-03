package com.example.pluslife.services

import com.example.pluslife.models.*
import retrofit2.Call
import retrofit2.http.*


interface Usuario {

    @POST("/usuario/login")
    fun login(@Query("email") email: String, @Query("senha") senha: String): Call<UsuarioModel>

    @POST("/usuario/doador")
    fun cadastroDoador(@Body body: CadastroDoadorRequest): Call<Void>

    @POST("/usuario/endereco")
    fun cadastroEndereco(@Body body: CadastroEnderecoRequest): Call<Void>

    @DELETE("/usuario/{id}")
    fun excluir(@Path("id") id: Int): Call<Void>

    @POST("/usuario/coordenadas")
    fun coordenadas(@Body body: UsuarioEnderecoRequest): Call<GeocodeResponse>

    @GET("usuario/endereco/{id}")
    fun getEndereco(@Path("id") id: Int): Call<EnderecoModel>
}