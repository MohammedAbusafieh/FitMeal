package com.example.fitmeal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Show current goal
        val prefs = getSharedPreferences("FitMealPrefs", MODE_PRIVATE)
        val goal = prefs.getString("goal", "Not set")
        findViewById<TextView>(R.id.txtWelcome).text = "🎯 Current Goal: $goal"

        // Button clicks
        findViewById<Button>(R.id.btnSetGoal).setOnClickListener {
            startActivity(Intent(this, SetGoalActivity::class.java))
        }
        findViewById<Button>(R.id.btnMealPlanner).setOnClickListener {
            startActivity(Intent(this, MealPlannerActivity::class.java))
        }
        findViewById<Button>(R.id.btnGroceryList).setOnClickListener {
            startActivity(Intent(this, GroceryListActivity::class.java))
        }
        findViewById<Button>(R.id.btnProgress).setOnClickListener {
            startActivity(Intent(this, ProgressActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                // Clear saved login info
                val prefs = getSharedPreferences("FitMealUsers", MODE_PRIVATE)
                prefs.edit().clear().apply()

                // Go back to login
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
