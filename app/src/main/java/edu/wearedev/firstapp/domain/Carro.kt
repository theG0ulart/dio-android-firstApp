package edu.wearedev.firstapp.domain

import java.util.UUID

data class Carro(
    val id: Int,
    val preco: String,
    val bateria: String,
    val potencia: String,
    val recarga: String,
    val urlPhoto: String,
)