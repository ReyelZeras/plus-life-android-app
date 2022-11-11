package com.example.pluslife.models

import java.io.Serializable

data class UsuarioEnderecoRequest(
    val email: String?,
    val bairro: String?,
    val rua: String?,
    val numero: Int?,
    val cidade: String?,
    val estado: String?,
): Serializable
