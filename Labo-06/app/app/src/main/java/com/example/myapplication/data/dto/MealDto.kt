package com.example.myapplication.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MealResponseDto(
    val meals: List<MealDto>?
)

@Serializable
data class MealDto(
    val idMeal: String? = null,
    val strMeal: String? = null,
    val strCategory: String? = null,
    val strArea: String? = null,
    val strMealThumb: String? = null
)
