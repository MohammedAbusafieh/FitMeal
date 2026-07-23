package com.example.fitmeal

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.fitmeal.viewmodel.GoalViewModel

class SetGoalActivity : AppCompatActivity() {

    private val goalViewModel: GoalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goal)

        // Toolbar setup
        val toolbar = findViewById<Toolbar>(R.id.toolbarSetGoal)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""   // remove "FitMeal" text

        val btnSave = findViewById<Button>(R.id.btnSaveGoal)
        val rbMuscleGain = findViewById<RadioButton>(R.id.rbMuscleGain)
        val rbFatLoss = findViewById<RadioButton>(R.id.rbFatLoss)
        val rbMaintenance = findViewById<RadioButton>(R.id.rbMaintenance)

        btnSave.setOnClickListener {

            val selectedGoal = when {
                rbMuscleGain.isChecked -> "Muscle Gain"
                rbFatLoss.isChecked -> "Fat Loss"
                rbMaintenance.isChecked -> "Maintenance"
                else -> null
            }

            if (selectedGoal == null) {
                Toast.makeText(this, "Please select a goal!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update LiveData
            goalViewModel.setGoal(selectedGoal)

            // Save to SharedPreferences
            val prefs = getSharedPreferences("FitMealPrefs", MODE_PRIVATE)
            prefs.edit().putString("goal", selectedGoal).apply()

            Toast.makeText(this, "Goal saved: $selectedGoal", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
