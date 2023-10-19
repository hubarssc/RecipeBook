package com.example.recipebook.listeners

import com.example.recipebook.model.RandomRecipeApiResponse

interface RandomRecipeResponseListener {
    fun didFetch(response: RandomRecipeApiResponse, message: String)
    fun didError(message: String)
}
