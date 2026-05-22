package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.view.HomeScreen
import com.example.myapplication.view.TaskScreen
import com.example.myapplication.viewmodel.GeneralViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {

                val navController = rememberNavController()


                val myViewModel: GeneralViewModel = viewModel()


                NavHost(navController = navController, startDestination = "home") {


                    composable("home") {
                        HomeScreen(onNavigateToTasks = {
                            navController.navigate("tasks")
                        })
                    }


                    composable("tasks") {
                        TaskScreen(
                            viewModel = myViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}