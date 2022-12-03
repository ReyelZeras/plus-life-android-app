package com.example.pluslife.models

data class EnderecoModel (
    val id: Long,
    val bairro: String,
    val rua: String,
    val numero: Int,
    val cidade: String,
    val estado: String,
    val latitude: Any,
    val longitude: Any,
    val usuarioId: UsuarioModel
)