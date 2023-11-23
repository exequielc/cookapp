package com.duo.myapplication.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MyViewModelFactory(private val mApplication: Application, vararg params: Any) :  ViewModelProvider.NewInstanceFactory() {
    private val mParams: Array<Any>

    init {
        mParams = params as Array<Any>
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return super.create(modelClass)

    }

}