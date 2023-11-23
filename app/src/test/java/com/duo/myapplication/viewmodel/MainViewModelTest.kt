package com.duo.myapplication.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.duo.myapplication.home.MainState
import com.duo.myapplication.model.Geo
import com.duo.myapplication.model.Recipe
import com.duo.myapplication.model.RecipeIngridient
import com.duo.myapplication.model.RecipesResponseModel
import com.duo.myapplication.model.Step
import com.duo.myapplication.services.RecipeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest{

    @RelaxedMockK
    private lateinit var quoteRepository: RecipeRepository

    @RelaxedMockK
    private lateinit var getDataCaseTest: GetDataCaseTest

    private lateinit var mainviewmodel : MainViewModel

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mainviewmodel = MainViewModel(quoteRepository)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    //
    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun `when service return data set on the livedata`() = runTest {
        //Given
        val geo : Geo = Geo(-32.00,-64.00)
        val listIng :List<RecipeIngridient> = listOf(  RecipeIngridient("ingridient","one") )
        val listStep :List<Step> = listOf(  Step("paso de prep"))
        var recipe = Recipe(geo,listIng ,listStep,"titulo","","desc",1)

        val mainstate : MainState =  MainState("",false,listOf(recipe))
        val recipesModel : RecipesResponseModel= RecipesResponseModel(listOf(recipe))

        coEvery { quoteRepository.getRecipes() } returns Result.success(recipesModel)

        //When
        mainviewmodel.getAllRecipes()

        //Then
        assert(mainviewmodel.state.value ==  mainstate)
    }


}