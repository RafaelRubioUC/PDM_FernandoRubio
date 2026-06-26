package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Meal
import com.example.myapplication.domain.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MealState {
    object Loading : MealState()
    data class Success(val meals: List<Meal>) : MealState()
    data class Error(val message: String) : MealState()
}

class MealViewModel(private val repository: MealRepository) : ViewModel() {

    private val _state = MutableStateFlow<MealState>(MealState.Loading)
    val state: StateFlow<MealState> = _state.asStateFlow()

    init {
        fetchMeals()
    }

    fun fetchMeals() {
        viewModelScope.launch {
            _state.value = MealState.Loading
            repository.getMeals()
                .onSuccess { meals ->
                    _state.value = MealState.Success(meals)
                }
                .onFailure { exception ->
                    _state.value = MealState.Error(exception.message ?: "Unknown error")
                }
        }
    }
}
