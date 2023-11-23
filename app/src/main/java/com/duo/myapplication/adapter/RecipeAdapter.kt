package com.duo.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.duo.myapplication.R
import com.duo.myapplication.model.Recipe
import com.duo.myapplication.databinding.AdapterCardRecipeBinding
import com.duo.myapplication.interfaces.ClickItemDetalListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


class RecipeAdapter(private val listener: ClickItemDetalListener): RecyclerView.Adapter<MainViewHolder>(),
    CoroutineScope {


    private val _listState = MutableLiveData<MutableList<Recipe>>()
    var list = mutableListOf<Recipe>()

    @SuppressLint("NotifyDataSetChanged")
    fun fillAdapter(movies: List<Recipe>) {
        this.list = movies.toMutableList()
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(AdapterCardRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int {
         return list.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
        }


        holder.itemView.setOnClickListener {
            listener.itemSelect(list[position])
        }

    }


    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(thumbs: ImageView, url: String) {
            Glide.with(thumbs)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .fallback(R.drawable.ic_launcher_foreground)
                .into(thumbs)
        }
    }

    lateinit var observerOnCategorySelected: Observer<Recipe>

    private val viewModelJob = Job()
    override val coroutineContext: CoroutineContext
        get() = viewModelJob + Dispatchers.Default


}
class MainViewHolder(val binding: AdapterCardRecipeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Recipe) {
        binding.p= movie
    }


}