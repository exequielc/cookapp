package com.duo.myapplication.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.duo.myapplication.R
import com.duo.myapplication.databinding.FragmentMapsBinding
import com.duo.myapplication.viewmodel.SharedViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class MapsFragment : Fragment() {
    private val sharedViewModel : SharedViewModel by activityViewModels()
    var _googleMap : GoogleMap? = null

    private val callback = OnMapReadyCallback { googleMap ->

       // val ros = LatLng(-32.950001, -60.666668)
       // googleMap.addMarker(MarkerOptions().position(ros).title("Imagine for a moment you are here"))
       // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ros,10f))


        _googleMap = googleMap

        sharedViewModel.viewModelScope.launch {
            try {
                if(sharedViewModel.getRecipe().geo!=null) {
                    val itemGeoRecipe = LatLng(
                        sharedViewModel.getRecipe().geo!!.lat,
                        sharedViewModel.getRecipe()!!.geo!!.lng
                    )
                    _googleMap?.addMarker(
                        MarkerOptions().position(itemGeoRecipe)
                            .title(sharedViewModel.getRecipe().title)
                    )
                    _googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(itemGeoRecipe, 14f))
                }
            }catch (ex : Exception){
                ex.printStackTrace()
            }

        }
    }
    lateinit var binding: FragmentMapsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)

        return binding.root
        //return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)



        binding.lnClose.setOnClickListener {

            activity?.onBackPressed()
        }


    }

    companion object {
        fun newInstance(

        ) = MapsFragment()
    }

}