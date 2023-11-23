package com.duo.myapplication.model

data class Recipe(
    val geo: Geo?,
    val recipeIngridient: List<RecipeIngridient>,
    val steps: List<Step>,
    val title: String,
    val url: String,
    val description: String,
    val cat: Int
)

data class RecipeResponse(
    val recipe: MutableList<Recipe> = mutableListOf()
)

