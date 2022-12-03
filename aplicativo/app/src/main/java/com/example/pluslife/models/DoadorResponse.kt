package com.example.pluslife.models

import java.time.LocalDate

data class DoadorResponse(
    val nome: String,
    val email: String,
    val dataNascimento: String,
    val tipoSanguineo: String
)