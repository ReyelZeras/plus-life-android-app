package com.example.pluslife.services

import com.example.pluslife.models.LoginRequest
import com.example.pluslife.models.UsuarioModel
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface Auth {

    @POST("/usuario/login")
    fun login(@Body body: LoginRequest): Call<UsuarioModel>

}