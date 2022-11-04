package com.example.pluslife.models

import java.time.LocalDate

data class DoadorResponse(
    val nome: String,
    val email: String,
    val dataNascimento: LocalDate,
    val tipoSanguineo: String
)