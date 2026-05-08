package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.serialization.Serializable

@Serializable object Home
@Serializable object NameList
@Serializable object SensorDetail

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Home,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Home> {
                            HomeScreen(
                                onNavigateToNames = { navController.navigate(NameList) },
                                onNavigateToSensors = { navController.navigate(SensorDetail) }
                            )
                        }
                        composable<NameList> {
                            NamesListScreen()
                        }
                        composable<SensorDetail> {
                            LightSensorScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onNavigateToNames: () -> Unit, onNavigateToSensors: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home", fontSize = 32.sp, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onNavigateToNames,
            modifier = Modifier.fillMaxWidth(0.8f))
        {
            Text("Ir a Lista de Nombres")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToSensors,
            modifier = Modifier.fillMaxWidth(0.8f))
        {
            Text("Probar Sensor de Luz")
        }
    }
}

@Composable
fun NamesListScreen() {
    val names = listOf("Rafael", "Alexa", "Karina", "Fernando", "Maria", "Jose")
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Lista de Alumnos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(names) { name ->
                ListItem(headlineContent = { Text(name) })
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun LightSensorScreen() {
    val sensorValues = useSensor(Sensor.TYPE_LIGHT)
    val lux = sensorValues.getOrNull(0) ?: 0f
    val isDark = lux < 10f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) Color(0xFF212121) else Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Sensor de Luz Ambiental",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDark) Color.White else Color.Black
        )
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(if (isDark) Color.DarkGray else Color(0xFFFFD600), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isDark) "OSCURO" else "LUZ",
                color = if (isDark) Color.White else Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            "Intensidad: $lux lx",
            fontSize = 18.sp,
            color = if (isDark) Color.White else Color.Black
        )
        Text(
            text = if (isDark) "Ambiente con poca luz" else "Ambiente iluminado",
            color = if (isDark) Color.LightGray else Color.Gray
        )
    }
}


@Composable
fun useSensor(sensorType: Int): List<Float> {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val sensor = sensorManager.getDefaultSensor(sensorType) ?: return emptyList()
    var sensorValues by remember { mutableStateOf(listOf(0f, 0f, 0f)) }

    DisposableEffect(sensorType) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.values?.let { sensorValues = it.toList() }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
        onDispose { sensorManager.unregisterListener(listener) }
    }
    return sensorValues
}