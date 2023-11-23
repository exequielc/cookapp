package com.duo.myapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.duo.myapplication.R
import com.duo.myapplication.adapter.RecipeAdapter
import com.duo.myapplication.databinding.ActivityMainBinding
import com.duo.myapplication.databinding.FragmentItemListBinding
import com.duo.myapplication.interfaces.ClickItemDetalListener
import com.duo.myapplication.interfaces.RecipesApi
import com.duo.myapplication.model.Recipe
import com.duo.myapplication.viewmodel.MainViewModel
import com.duo.myapplication.services.RecipeRepository
import com.duo.myapplication.factory.ViewModelFactory
import com.duo.myapplication.model.RecipeIngridient
import com.duo.myapplication.viewmodel.SharedViewModel
import com.google.android.material.search.SearchView
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.system.exitProcess

class ListItemsFragment : Fragment()  , ClickItemDetalListener {
    private val TAG = "ItemFragment"
    private val sharedViewModel : SharedViewModel by activityViewModels()
    lateinit var viewModel : MainViewModel
    lateinit var binding: FragmentItemListBinding
    var list =  ArrayList<Recipe>()

    val adapter = RecipeAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


       viewModel = ViewModelProvider(this, ViewModelFactory(RecipeRepository(RecipesApi.intance))).get(            MainViewModel::class.java)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_list, container, false)


        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        viewModel.state.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")

            if(it.isLoading==false && it.recipes.isNotEmpty())
            adapter.fillAdapter(it.recipes)

            if(it.isLoading==false && it.messaje!=null && it.messaje.isNotEmpty()){
                Toast.makeText(activity , "No Data found", Toast.LENGTH_SHORT).show()
                adapter.fillAdapter(emptyList())
            }
        })


        binding.progress.visibility = View.VISIBLE
        viewModel.viewModelScope.launch {
            viewModel.getAllRecipes()
            binding.progress.visibility = View.GONE
        }

        viewModel.state.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")

            if(it!=null && it.recipes.isNotEmpty()) {
                list = it.recipes as ArrayList<Recipe>
                adapter.fillAdapter(it.recipes)
            }
        })

        binding.retry.setOnClickListener {

            binding.progress.visibility = View.VISIBLE
            viewModel.viewModelScope.launch {
                viewModel.getAllRecipes()
                binding.progress.visibility = View.GONE
            }
        }



        binding.searchView.setOnQueryTextListener(
            object : SearchViewBindingAdapter.OnQueryTextSubmit,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d(TAG , "searchView  onQueryTextSubmit" +query.toString())
                    filterList(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d(TAG , " searchView onQueryTextChange" +newText.toString())
                    filterList(newText)
                    return true
                }
            })



    }


    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<Recipe>()
            for (i in list) {
                if (i.title.lowercase(Locale.ROOT).contains(query.lowercase())) {
                    filteredList.add(i)
                }else{
                   val recipeIngridient : RecipeIngridient? =  i.recipeIngridient.find {
                        it.ingridient.lowercase(Locale.ROOT).contains(query.lowercase())

                    }
                    if(recipeIngridient!=null)
                    filteredList.add( i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(activity , "No Data found", Toast.LENGTH_SHORT).show()
                adapter.fillAdapter(emptyList())
            } else {
                adapter.fillAdapter(filteredList)
            }
        }
    }

    companion object {
        fun newInstance(

        ) = MapsFragment()
    }

    override fun itemSelect(data: Recipe) {

        sharedViewModel.saveRecipe(data)

        //Do I have to send another params ?
        val bundle = Bundle().apply {
            putString("ADITIONAL_PARAM","")
        }
        val fragInfo = ItemDetailFragment()
        fragInfo.arguments = bundle


        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(android.R.id.content,fragInfo)
            ?.addToBackStack(null)
            ?.commit()



    }

}