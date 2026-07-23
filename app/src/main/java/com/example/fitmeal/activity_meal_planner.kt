package com.example.fitmeal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fitmeal.data.AppDatabase
import com.example.fitmeal.data.Ingredient
import com.example.fitmeal.data.Meal
import com.example.fitmeal.repository.MealRepository
import kotlinx.coroutines.launch

class MealPlannerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_planner)

        val editBreakfast = findViewById<EditText>(R.id.editBreakfast)
        val editLunch = findViewById<EditText>(R.id.editLunch)
        val editDinner = findViewById<EditText>(R.id.editDinner)
        val editSnacks = findViewById<EditText>(R.id.editSnacks)
        val btnSaveMeals = findViewById<Button>(R.id.btnSaveMeals)

        val prefs = getSharedPreferences("FitMealPrefs", MODE_PRIVATE)

        // Load saved meals if they exist
        editBreakfast.setText(prefs.getString("breakfast", ""))
        editLunch.setText(prefs.getString("lunch", ""))
        editDinner.setText(prefs.getString("dinner", ""))
        editSnacks.setText(prefs.getString("snacks", ""))

        // Initialize Room
        val db = AppDatabase.getDatabase(this)
        val repository = MealRepository(db.mealDao())

        btnSaveMeals.setOnClickListener {
            val breakfast = editBreakfast.text.toString()
            val lunch = editLunch.text.toString()
            val dinner = editDinner.text.toString()
            val snacks = editSnacks.text.toString()

            // Save meals in SharedPreferences
            val editor = prefs.edit()
            editor.putString("breakfast", breakfast)
            editor.putString("lunch", lunch)
            editor.putString("dinner", dinner)
            editor.putString("snacks", snacks)
            editor.apply()

            // Food data for calorie/protein calculation
            val foodData = mapOf(
                "chicken" to Pair(165, 31),
                "rice" to Pair(130, 2),
                "oat" to Pair(150, 5),
                "egg" to Pair(70, 6),
                "banana" to Pair(100, 1),
                "milk" to Pair(120, 8),
                "bread" to Pair(80, 3),
                "fish" to Pair(200, 25),
                "salad" to Pair(50, 2),
                "beef" to Pair(250, 26),
                "pasta" to Pair(220, 7),
                "apple" to Pair(90, 0)
            )

            val mealNames = listOf(breakfast, lunch, dinner, snacks)
            val defaultIngredients = listOf("Chicken Breast", "Rice", "Salad", "Banana") // can customize

            lifecycleScope.launch {
                // Clear old meals and ingredients
                repository.deleteAllIngredients()
                repository.deleteAllMeals()

                var totalCalories = 0
                var totalProtein = 0

                for (i in mealNames.indices) {
                    val mealName = mealNames[i]
                    if (mealName.isNotEmpty()) {
                        // Calculate calories & protein for this meal
                        var mealCalories = 0
                        var mealProtein = 0
                        for ((food, data) in foodData) {
                            if (mealName.contains(food, true)) {
                                mealCalories += data.first
                                mealProtein += data.second
                            }
                        }

                        totalCalories += mealCalories
                        totalProtein += mealProtein

                        // Insert meal into Room with calculated calories & protein
                        val mealId = repository.insertMeal(
                            Meal(mealName = mealName, calories = mealCalories, protein = mealProtein)
                        )

                        // Insert ingredient
                        repository.insertIngredient(
                            Ingredient(mealOwnerId = mealId.toInt(), ingredientName = defaultIngredients[i])
                        )
                    }
                }

                // Save total calories & protein
                editor.putInt("totalCalories", totalCalories)
                editor.putInt("totalProtein", totalProtein)
                editor.apply()

                Toast.makeText(
                    this@MealPlannerActivity,
                    "Meal plan saved!\nCalories: $totalCalories kcal | Protein: $totalProtein g",
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }
    }
}
