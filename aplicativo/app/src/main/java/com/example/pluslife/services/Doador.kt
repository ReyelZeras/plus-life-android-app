package com.example.pluslife.services

import com.example.pluslife.models.DoadorModel
import com.example.pluslife.models.DoadorResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface Doador {

    @PUT("/doador")
    fun atualizar(@Body body: DoadorModel): Call<Void>

    @GET("/doador/{id}")
    fun getDoador(@Path("id") idDoador: String): Call<DoadorResponse>
}