package com.betsson.interviewtest.domain.model

data class Bet(
    val type: String,
    val sellIn: Int,
    val odds: Int,
    val image: String
)
