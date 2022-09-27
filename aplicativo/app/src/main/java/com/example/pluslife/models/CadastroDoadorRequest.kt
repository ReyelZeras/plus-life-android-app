package com.example.pluslife.models

data class CadastroDoadorRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val nascimento: String,
    val tipoSanguineo: String,
)