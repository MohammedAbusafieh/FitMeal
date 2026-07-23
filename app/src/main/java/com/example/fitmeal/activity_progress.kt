package com.example.fitmeal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class ProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        val prefs = getSharedPreferences("FitMealPrefs", MODE_PRIVATE)

        val goal = prefs.getString("goal", "Not set")
        val breakfast = prefs.getString("breakfast", "Not set")
        val lunch = prefs.getString("lunch", "Not set")
        val dinner = prefs.getString("dinner", "Not set")
        val snacks = prefs.getString("snacks", "Not set")
        val lastGroceries = prefs.getString("lastGroceries", "No grocery list saved yet.")
        val calories = prefs.getInt("totalCalories", 0)
        val protein = prefs.getInt("totalProtein", 0)

        val textView = findViewById<TextView>(R.id.txtGoal)
        textView.text = """
            🔥 Your Current Goal: $goal

            🍳 Meal Plan:
            • Breakfast: $breakfast
            • Lunch: $lunch
            • Dinner: $dinner
            • Snacks: $snacks

            🧾 Grocery List (last confirmed):
            $lastGroceries

            💪 Nutrition Summary:
            • Calories: $calories kcal
            • Protein: $protein g
        """.trimIndent()
    }
}
