package com.example.pluslife.services

import com.example.pluslife.models.PostagemModel
import com.example.pluslife.models.PostagemRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Postagem {

    @GET("/postagem")
    fun getPostagens(): Call<List<PostagemModel>>

    @POST("/postagem")
    fun novaPostagem(@Body postagem: PostagemRequest): Call<Void>

    @DELETE("/postagem/{id}")
    fun deletePostagem(@Path("id") idPostagem: Long): Call<Void>
}