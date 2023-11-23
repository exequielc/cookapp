package com.duo.myapplication.services

import com.duo.myapplication.interfaces.RecipesApi
import com.duo.myapplication.model.RecipesResponseModel

class RecipeRepository(private val api: RecipesApi) {


    suspend fun getRecipes(): Result<RecipesResponseModel> {
       return try {
           return Result.success( api.getRecipies())

        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}