package com.example.recipebook

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.adapters.RandomRecipeAdapter
import com.example.recipebook.di.RequestManager
import com.example.recipebook.listeners.RandomRecipeResponseListener
import com.example.recipebook.model.RandomRecipeApiResponse

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var requestManager: RequestManager
    private lateinit var randomRecipeAdapter: RandomRecipeAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)

        requestManager = RequestManager(this)

        recyclerView = findViewById(R.id.recycler_random)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        randomRecipeAdapter = RandomRecipeAdapter(this, emptyList())
        recyclerView.adapter = randomRecipeAdapter

        requestManager.getRandomRecipes(randomRecipeResponseListener)

    }

    private val randomRecipeResponseListener = object : RandomRecipeResponseListener {
        override fun didFetch(response: RandomRecipeApiResponse, message: String) {
            randomRecipeAdapter = RandomRecipeAdapter(this@MainActivity, response.recipes)
            recyclerView.adapter = randomRecipeAdapter
            progressBar.visibility = View.GONE
        }

        override fun didError(message: String) {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
        }
    }
}