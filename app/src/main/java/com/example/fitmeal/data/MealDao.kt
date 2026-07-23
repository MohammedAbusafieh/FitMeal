package com.example.fitmeal.data

import androidx.room.*

@Dao
interface MealDao {

    @Insert
    suspend fun insertMeal(meal: Meal): Long

    @Insert
    suspend fun insertIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<Meal>

    @Query("SELECT * FROM ingredients WHERE mealOwnerId = :mealId")
    suspend fun getIngredientsForMeal(mealId: Int): List<Ingredient>

    // ✅ Delete all meals
    @Query("DELETE FROM meals")
    suspend fun deleteAllMeals()

    // ✅ Delete all ingredients
    @Query("DELETE FROM ingredients")
    suspend fun deleteAllIngredients()
}

