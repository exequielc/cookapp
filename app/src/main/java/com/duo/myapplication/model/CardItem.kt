package com.duo.myapplication.model

data class CardItem(
    val title: String,
    val url: String,
    val ingridients: List<String>,
    val steps: List<String>,
    val cat: Int,
    val lat: Double,
    val lng: Double,
)