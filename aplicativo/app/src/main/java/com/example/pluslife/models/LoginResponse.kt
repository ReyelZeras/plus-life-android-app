package com.example.pluslife.models

data class LoginResponse(
    val id: Int,
    val nome: String,
    val email: String,
    val senha: String,
    val dataCriacao: String,
    val autenticado: Boolean,
    val tipoUsuario: TipoUsuario,
)
