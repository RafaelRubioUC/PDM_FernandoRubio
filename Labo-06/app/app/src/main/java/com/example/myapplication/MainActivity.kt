package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.network.ktorClient
import com.example.myapplication.data.repository.MealRepositoryImpl
import com.example.myapplication.presentation.MealScreen
import com.example.myapplication.presentation.MealViewModel
import com.example.myapplication.presentation.ViewModelFactory
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Manual DI for simplicity in this exercise
        val repository = MealRepositoryImpl(ktorClient)
        val factory = ViewModelFactory(repository)

        setContent {
            MyApplicationTheme {
                val viewModel: MealViewModel = viewModel(factory = factory)
                MealScreen(viewModel = viewModel)
            }
        }
    }
}
