package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.MealDto
import com.example.myapplication.domain.model.Meal

fun MealDto.toModel(): Meal {
    return Meal(
        id = idMeal ?: "",
        name = strMeal ?: "Unknown",
        category = strCategory ?: "Unknown",
        area = strArea ?: "Unknown",
        imageUrl = strMealThumb ?: ""
    )
}
