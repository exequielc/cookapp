package com.duo.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.duo.myapplication.model.Recipe

class SharedViewModel : ViewModel() {
    private var _recipe  = MutableLiveData<Recipe>()
    val recipe : LiveData<Recipe> = _recipe

    fun saveRecipe(newRecipe: Recipe){
        _recipe.value = newRecipe
    }

    fun getRecipe() : Recipe{
        return recipe.value!!
    }
}