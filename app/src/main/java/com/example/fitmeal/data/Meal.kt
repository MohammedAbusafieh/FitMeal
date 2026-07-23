package com.example.fitmeal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey(autoGenerate = true) val mealId: Int = 0,
    val mealName: String,
    val calories: Int,
    val protein: Int // ✅ Add protein here
)
