package com.example.pluslife.models

import java.time.LocalDate

data class DoadorModel(
    var id: String?,
    var nome: String?,
    var email: String?,
    var nascimento: LocalDate?,
    var tipoSanguineo: String?
)
