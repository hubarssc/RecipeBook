package com.example.recipebook.model

data class Step(
    val number: Int,
    val step: String,
    val ingredients: ArrayList<Ingredient>,
    val equipment: ArrayList<Equipment>,
    val length: Length
)