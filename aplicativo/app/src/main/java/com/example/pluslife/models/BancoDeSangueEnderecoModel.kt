package com.example.pluslife.models

data class BancoDeSangueEnderecoModel(
    val id: Long,
    val latitude: Float,
    val longitude: Float,
    val nome: String,
    val rua: String,
    val numero: Int,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val telefone: String,
    val emailContato: String,
    val estoque: ArrayList<NomeNivelSang>,
    val distancia: Int
)
