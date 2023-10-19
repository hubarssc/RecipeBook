package com.example.recipebook

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
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
    private lateinit var spinner: Spinner
    private var tags: MutableList<String> = mutableListOf()

    private val spinnerSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
            // Ваш код, который выполняется при выборе элемента в Spinner
            val selectedTag = spinner.selectedItem.toString()
            tags.clear()
            tags.add(selectedTag)
            requestManager.getRandomRecipes(randomRecipeResponseListener, tags)
            progressBar.visibility = View.VISIBLE
        }

        override fun onNothingSelected(adapterView: AdapterView<*>?) {
            // Ваш код, который выполняется, если ничего не выбрано в Spinner
        }
    }



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

        spinner = findViewById(R.id.spinner_tags)
        val arrayAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.tags,
            R.layout.spinner_text
        )
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = spinnerSelectedListener

        //requestManager.getRandomRecipes(randomRecipeResponseListener)
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