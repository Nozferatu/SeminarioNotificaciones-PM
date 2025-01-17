package com.cmj.seminarionotificaciones.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cmj.seminarionotificaciones.core.navigation.screens.Ejercicio1

@Composable
fun NavigationWrapper(actividadInicial: String) {
    val contexto = LocalContext.current
    val navController = rememberNavController()

    if(actividadInicial.isNotEmpty()){
        when(actividadInicial){
            "Ejercicio" -> {navController.navigate(Ejercicio1)}
        }
    }

    NavHost(navController = navController, startDestination = Inicio){
        composable<Inicio>{
            Inicio { navController.navigate(Ejercicio1) }
        }
        composable<Ejercicio1>{
            Ejercicio1(contexto = contexto)
        }
    }
}