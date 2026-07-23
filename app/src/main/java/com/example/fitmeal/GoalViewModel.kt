package com.example.fitmeal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GoalViewModel : ViewModel() {

    private val _goal = MutableLiveData<String>()
    val goal: LiveData<String> get() = _goal

    fun setGoal(newGoal: String) {
        _goal.value = newGoal
    }
}
