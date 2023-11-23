package com.duo.myapplication.interfaces

import com.duo.myapplication.model.Recipe
import com.duo.myapplication.model.RecipesResponseModel

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface RecipesApi {
    companion object {
        val intance =  Retrofit.Builder().baseUrl("https://demo4030749.mockable.io/").addConverterFactory(MoshiConverterFactory.create()).client(
            OkHttpClient.Builder().build()
        ).build().create(RecipesApi::class.java)
    }

    @GET("listrecipes")
    suspend fun getRecipies() : RecipesResponseModel



}