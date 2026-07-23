package com.example.fitmeal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val ingredientId: Int = 0,
    val mealOwnerId: Int,   // Foreign key pointing to Meal
    val ingredientName: String
)
