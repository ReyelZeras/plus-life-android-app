package com.example.pluslife.models

data class UsuarioModel(
    val id: Int,
    val nome: String,
    val email: String,
    val senha: String,
    val dataCriacao: String,
    val autenticado: Int,
    val tipoUsuario: TipoUsuario,
)
