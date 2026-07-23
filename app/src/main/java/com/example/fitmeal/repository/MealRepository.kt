package com.example.fitmeal.repository

import com.example.fitmeal.data.*

class MealRepository(private val mealDao: MealDao) {

    suspend fun insertMeal(meal: Meal): Long {
        return mealDao.insertMeal(meal)
    }

    suspend fun insertIngredient(ingredient: Ingredient) {
        mealDao.insertIngredient(ingredient)
    }

    suspend fun deleteAllMeals() {
        mealDao.deleteAllMeals()
    }

    suspend fun deleteAllIngredients() {
        mealDao.deleteAllIngredients()
    }

    suspend fun getAllMeals(): List<Meal> {
        return mealDao.getAllMeals()
    }

    suspend fun getIngredientsForMeal(mealId: Int): List<Ingredient> {
        return mealDao.getIngredientsForMeal(mealId)
    }
}
