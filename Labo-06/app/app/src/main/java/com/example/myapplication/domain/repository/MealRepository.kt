package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Meal

interface MealRepository {
    suspend fun getMeals(): Result<List<Meal>>
}
