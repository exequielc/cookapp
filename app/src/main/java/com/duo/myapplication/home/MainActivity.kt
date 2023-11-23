package com.duo.myapplication.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.duo.myapplication.databinding.ActivityMainBinding
import com.duo.myapplication.fragments.ListItemsFragment
import com.duo.myapplication.fragments.MapsFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.duo.myapplication.R.layout.activity_main)
        setContentView(binding.root)




    }


    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount <= 1) {
            super.onBackPressed()
        } else {
            fragmentManager.popBackStack()
        }
    }
}