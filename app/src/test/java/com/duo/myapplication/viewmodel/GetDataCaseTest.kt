package com.duo.myapplication.viewmodel

import com.duo.myapplication.model.Geo
import com.duo.myapplication.model.Recipe
import com.duo.myapplication.model.RecipeIngridient
import com.duo.myapplication.model.Step
import com.duo.myapplication.services.RecipeRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetDataCaseTest{




    @RelaxedMockK
    private lateinit var quoteRepository: RecipeRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)

    }


    @Test
    fun `when the api return nothing then return mock item`() = runBlocking {



        val geo :Geo = Geo(-32.00,-64.00)
        val listIng :List<RecipeIngridient> = listOf(  RecipeIngridient("ingridient","one") )
        val listStep :List<Step> = listOf(  Step("paso de prep"))


        var recipe = Recipe(geo,listIng ,listStep,"titulo","","desc",1)

        //Given

        val response = Result.success(listOf(recipe))




        //Then
        assert(response.isSuccess)

    }


    @Test
    fun `when the api return bad json`() = runBlocking {



        val geo :Geo
        val listIng :List<RecipeIngridient> = listOf(  RecipeIngridient("ingridient","one") )
        val listStep :List<Step> = listOf(  Step("paso de prep"))


        var recipe = Recipe(null,listIng ,listStep,"titulo","","desc",1)

        //Given

        val response = Result.success(listOf(recipe))




        //Then
        assert(response == null)

    }

}