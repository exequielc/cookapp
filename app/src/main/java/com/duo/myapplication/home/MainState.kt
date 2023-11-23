package com.duo.myapplication.home

import com.duo.myapplication.model.Recipe

data class MainState(
    val messaje: String = "",
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList()
)