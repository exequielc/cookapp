package com.duo.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.duo.myapplication.home.MainState
import com.duo.myapplication.model.Recipe
import com.duo.myapplication.services.RecipeRepository

class MainViewModel constructor(private val repository: RecipeRepository)  : ViewModel() {
    var state =   MutableLiveData<MainState>()

    lateinit var observerOnCategorySelected: Observer<Recipe>


    private val _itemSelected = MutableLiveData<Recipe>()
    var itemDataSelected: Recipe? = null


    suspend fun getAllRecipes() {
        val response = repository.getRecipes()

        response.onSuccess {
            state.postValue( MainState("", false, it.recipes))
        }
        response.onFailure {
            state.postValue(MainState(it.message.toString(), false, emptyList<Recipe>()))
        }

    }


    init {
        initObserver()
    }

    private fun initObserver() {
        observerOnCategorySelected = Observer { value ->
            value.let {
                _itemSelected.value = it
            }
        }
    }


    fun clearSelection() {
        _itemSelected.value = null
    }

    fun setItemSelection(item: Recipe) {
        itemDataSelected = item
    }



}