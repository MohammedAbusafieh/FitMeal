package com.example.fitmeal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class GroceryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocery_list)

        val prefs = getSharedPreferences("FitMealPrefs", MODE_PRIVATE)
        val allMeals = listOf(
            prefs.getString("breakfast", "") ?: "",
            prefs.getString("lunch", "") ?: "",
            prefs.getString("dinner", "") ?: "",
            prefs.getString("snacks", "") ?: ""
        )

        // Generate grocery items from meals (keyword matching)
        val groceries = mutableSetOf<String>()
        for (meal in allMeals) {
            if (meal.contains("chicken", true)) groceries.add("Chicken Breast")
            if (meal.contains("rice", true)) groceries.add("Rice")
            if (meal.contains("oat", true)) groceries.add("Oats")
            if (meal.contains("egg", true)) groceries.add("Eggs")
            if (meal.contains("bread", true)) groceries.add("Whole Wheat Bread")
            if (meal.contains("banana", true)) groceries.add("Bananas")
            if (meal.contains("milk", true)) groceries.add("Milk")
            if (meal.contains("salad", true)) groceries.add("Vegetables")
            if (meal.contains("fish", true)) groceries.add("Salmon or Tuna")
        }

        val groceryList = groceries.ifEmpty {
            mutableListOf("No meals selected yet! Go to Meal Planner first.")
        }.toList()

        val listView = findViewById<ListView>(R.id.listGroceries)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            groceryList
        )
        listView.adapter = adapter
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        btnConfirm.setOnClickListener {
            val selected = mutableListOf<String>()
            for (i in 0 until listView.count) {
                if (listView.isItemChecked(i)) {
                    selected.add(groceryList[i])
                }
            }

            if (selected.isEmpty()) {
                Toast.makeText(this, "No groceries selected", Toast.LENGTH_SHORT).show()
            } else {
                val joined = selected.joinToString(", ")
                val editor = prefs.edit()
                editor.putString("lastGroceries", joined)
                editor.apply()

                Toast.makeText(this, "Grocery list saved:\n$joined", Toast.LENGTH_LONG).show()


                finish()
            }
        }
    }
}
