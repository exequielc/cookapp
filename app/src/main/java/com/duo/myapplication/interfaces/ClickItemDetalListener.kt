package com.duo.myapplication.interfaces

import com.duo.myapplication.model.Recipe

interface ClickItemDetalListener {
    fun itemSelect(data: Recipe)
}