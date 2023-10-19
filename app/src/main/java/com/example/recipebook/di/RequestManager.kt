package com.example.recipebook.di

import android.content.Context
import android.util.Log
import com.example.recipebook.BuildConfig
import com.example.recipebook.listeners.RandomRecipeResponseListener
import com.example.recipebook.model.RandomRecipeApiResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RequestManager(private val context: Context) {

    private val apiKey: String = BuildConfig.API_KEY
    init {
        Log.d("API_KEY", "API Key: $apiKey")
    }


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: CallRandomRecipes = retrofit.create(CallRandomRecipes::class.java)

    // Метод для получения случайных рецептов
    fun getRandomRecipes(listener: RandomRecipeResponseListener) {
        val call = apiService.callRandomRecipe(apiKey, "20")

        call.enqueue(object : Callback<RandomRecipeApiResponse> {
            override fun onResponse(call: Call<RandomRecipeApiResponse>, response: Response<RandomRecipeApiResponse>) {
                if (response.isSuccessful) {
                    Log.d("API_RESPONSE", "Successful response received")
                    Log.d("API_RESPONSE", "Response code: ${response.code()}")
                    Log.d("API_RESPONSE", "Response body: ${Gson().toJson(response.body())}")
                    response.body()?.let { listener.didFetch(it, response.message()) }
                } else {
                    Log.e("API_RESPONSE", "API Error: ${response.code()} - ${response.message()}")
                    val errorBody = response.errorBody()?.string()
                    Log.e("API_RESPONSE", "Error body: $errorBody")
                    listener.didError(response.message())

                }
            }

            override fun onFailure(call: Call<RandomRecipeApiResponse>, t: Throwable) {
                Log.e("API_RESPONSE", "API Request Failed: ${t.message}")
                listener.didError(t.message ?: "")
            }
        })
    }

    private interface CallRandomRecipes {
        @GET("recipes/random")
        fun callRandomRecipe(
            @Query("apiKey") apiKey: String,
            @Query("number") number: String
        ): Call<RandomRecipeApiResponse>
    }
}
