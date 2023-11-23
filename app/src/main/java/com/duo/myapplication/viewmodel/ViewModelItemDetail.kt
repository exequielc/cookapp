package com.duo.myapplication.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.duo.myapplication.model.Recipe

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ViewModelItemDetail(app: Application) : AndroidViewModel(app), CoroutineScope {

    private val _itemSelected = MutableLiveData<Recipe>()
    var itemDataSelected: Recipe? = null

    private val _listState = MutableLiveData<MutableList<Recipe>>()
    var listState: LiveData<MutableList<Recipe>> = _listState

    private val _progressState = MutableLiveData<Boolean>()
    var progressState: LiveData<Boolean> = _progressState

    //private val repository = PokemonRepository()
    lateinit var observerOnCategorySelected: Observer<Recipe>

    private val viewModelJob = Job()
    override val coroutineContext: CoroutineContext
        get() = viewModelJob + Dispatchers.Default

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

    fun fetchPokemonData() {
        _progressState.value = true
       /* viewModelScope.launch {
            val response = repository.getPokemon()
            response?.body()?.pokemon.let { list ->
                _listState.value = list
            }
        }*/
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // Memory leak
}
