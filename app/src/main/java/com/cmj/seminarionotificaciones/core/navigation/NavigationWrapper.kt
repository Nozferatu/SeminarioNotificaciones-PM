package com.cmj.seminarionotificaciones.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cmj.seminarionotificaciones.core.navigation.screens.*

@Composable
fun NavigationWrapper(actividadSeleccionada: String) {
    val contexto = LocalContext.current
    val navController = rememberNavController()
    val startDestination: Any = when(actividadSeleccionada){
        "Ejercicio1" -> { Ejercicio1 }
        else -> { Inicio }
    }

    NavHost(navController = navController, startDestination = startDestination){
        composable<Inicio>{
            Inicio(navController)
        }
        composable<Ejercicio1>{
            Ejercicio1(contexto = contexto)
        }
        composable<Ejercicio2>{
            Ejercicio2(contexto = contexto)
        }
        composable<Ejercicio3>{
            Ejercicio3(contexto = contexto)
        }
    }
}