package com.example.pluslife.models

import java.time.LocalDateTime

data class PostagemModel(
    var id: Long,
    var descricao: String,
    var dataHora: String,
    var usuarioEntity: UsuarioModel
)
