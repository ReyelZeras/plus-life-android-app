package com.example.pluslife.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {

    //Emulador
//    val baseURL = "http://10.0.2.2:8080/"

    //Celular
    val baseURL = "http://34.224.190.38:8080/"

    fun getInstance(): Retrofit{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseURL).build()
    }

}