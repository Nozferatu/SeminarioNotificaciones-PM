package com.cmj.seminarionotificaciones.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationWrapper() {
    val contexto = LocalContext.current
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Inicio){
        composable<Inicio>{
            Inicio { navController.navigate(Ejercicio1) }
        }
        composable<Ejercicio1>{
            Ejercicio1(contexto = contexto)
        }
    }
}