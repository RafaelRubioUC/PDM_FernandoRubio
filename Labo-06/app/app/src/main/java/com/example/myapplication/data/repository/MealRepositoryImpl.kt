package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.MealResponseDto
import com.example.myapplication.data.mapper.toModel
import com.example.myapplication.domain.model.Meal
import com.example.myapplication.domain.repository.MealRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class MealRepositoryImpl(private val client: HttpClient) : MealRepository {
    override suspend fun getMeals(): Result<List<Meal>> {
        return try {
            val response: MealResponseDto = client.get("https://www.themealdb.com/api/json/v1/1/search.php?s=").body()
            val meals = response.meals?.map { it.toModel() } ?: emptyList()
            Result.success(meals)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
