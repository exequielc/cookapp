package com.duo.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.duo.myapplication.R
import com.duo.myapplication.adapter.ItemSimpleAdapter
import com.duo.myapplication.databinding.FragmentItemDetailBinding
import com.duo.myapplication.fragments.placeholder.PlaceholderContent
import com.duo.myapplication.interfaces.ClickItemDetalListener
import com.duo.myapplication.model.Recipe
import com.duo.myapplication.viewmodel.SharedViewModel
import com.google.android.gms.maps.MapFragment


class ItemDetailFragment : Fragment(), ClickItemDetalListener {
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding
    val sharedViewModel : SharedViewModel by activityViewModels()
    var recipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val verificationId = this.arguments?.getString("PARAM")
        Log.d("TAG","PARAMPARAM" + verificationId)

        recipe = sharedViewModel.getRecipe()



    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =  FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding?.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recipedetail = recipe

            Glide.with(this)
                .load(recipe?.url)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .fallback(R.drawable.ic_launcher_background)
                .into(binding?.img!!)


        var listIngridients : ArrayList<PlaceholderContent.PlaceholderItem> =ArrayList<PlaceholderContent.PlaceholderItem>()
        recipe?.recipeIngridient?.forEach {
            listIngridients.add(PlaceholderContent.PlaceholderItem("○", it.ingridient,it.quantity))
        }
        binding?.recyclerviewIngridients?.adapter = ItemSimpleAdapter(listIngridients)
        binding?.recyclerviewIngridients?.layoutManager = LinearLayoutManager(context)

        var numStep  = 1
        var listSteps : ArrayList<PlaceholderContent.PlaceholderItem> = ArrayList<PlaceholderContent.PlaceholderItem>()
        recipe?.steps?.forEach {

            listSteps.add(PlaceholderContent.PlaceholderItem(numStep++.toString(), it.detail,""))

        }
        binding?.recyclerviewSteps?.adapter = ItemSimpleAdapter(listSteps)
        binding?.recyclerviewSteps?.layoutManager = LinearLayoutManager(context)


        binding?.lnClose?.setOnClickListener {
            //findNavController().popBackStack()
            getActivity()?.onBackPressed();
        }


        binding?.irgeo?.setOnClickListener {
           // sharedViewModel.saveRecipe(sharedViewModel.getRecipe())


            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(android.R.id.content, MapsFragment())
                ?.addToBackStack(null)
                ?.commit()

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {

        fun newInstance (context: Context, _recipe: Recipe) = run {


        }

    }

    override fun itemSelect(data: Recipe) {

    }


}