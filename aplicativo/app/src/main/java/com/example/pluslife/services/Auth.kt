package com.example.pluslife.services

import com.example.pluslife.models.LoginRequest
import com.example.pluslife.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface Auth {

    @POST("/usuario/login")
    fun login(@Body body: LoginRequest): Call<LoginResponse>

}