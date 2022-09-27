package com.example.pluslife.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {

    //Emulador
    val baseURL = "http://10.0.2.2:3000/"

    //Celular
    //    val baseURL = "http://IP_DA_MAQUINA:3000/"

    fun getInstance(): Retrofit{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseURL).build()
    }

}