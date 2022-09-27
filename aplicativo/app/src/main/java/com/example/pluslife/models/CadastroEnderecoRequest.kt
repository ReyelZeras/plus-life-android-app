package com.example.pluslife.models

data class CadastroEnderecoRequest (
    val email: String,
    val bairro: String,
    val rua: String,
    val numero: Int,
    val cidade: String,
    val estado: String,
)