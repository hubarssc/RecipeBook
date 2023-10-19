package com.example.recipebook.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.R
import com.example.recipebook.model.Recipe
import com.squareup.picasso.Picasso

class RandomRecipeAdapter(
    private val context: Context,
    private val recipeList: List<Recipe>
) : RecyclerView.Adapter<RandomRecipeAdapter.RandomRecipeViewHolder>() {

    class RandomRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var randomListContainer: CardView
        var textViewTitle: TextView
        var textViewServings: TextView
        var textViewLike: TextView
        var textViewTime: TextView
        var imageViewFood: ImageView

        init {
            randomListContainer = itemView.findViewById(R.id.random_list_container)
            textViewTitle = itemView.findViewById(R.id.textView_title)
            textViewServings = itemView.findViewById(R.id.textView_servings)
            textViewLike = itemView.findViewById(R.id.textView_like)
            textViewTime = itemView.findViewById(R.id.textView_time)
            imageViewFood = itemView.findViewById(R.id.imageView_food)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomRecipeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false)
        return RandomRecipeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: RandomRecipeViewHolder, position: Int) {
        val recipe = recipeList[position]

        holder.textViewTitle.text = recipe.title
        holder.textViewTitle.isSelected = true

        val likesText = context.getString(R.string.likes_template, recipe.aggregateLikes)
        holder.textViewLike.text = likesText

        val servingsText = context.getString(R.string.servings_template, recipe.servings)
        holder.textViewServings.text = servingsText

        val minutesText = context.getString(R.string.minutes_template, recipe.readyInMinutes)
        holder.textViewTime.text = minutesText

        Picasso.get().load(recipe.image).into(holder.imageViewFood)
    }
}





