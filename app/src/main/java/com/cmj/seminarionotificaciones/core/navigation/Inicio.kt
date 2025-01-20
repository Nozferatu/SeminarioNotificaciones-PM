package com.cmj.seminarionotificaciones.core.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Inicio(navController: NavHostController){
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(.5f))
        Text(
            "Inicio",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.weight(.2f))
        Button(
            onClick = { navController.navigate(Ejercicio1) }
        ) { Text("Ejercicio 1") }
        Spacer(modifier = Modifier.weight(.2f))
        Button(
            onClick = { navController.navigate(Ejercicio2) }
        ) { Text("Ejercicio 2") }
        Spacer(modifier = Modifier.weight(.2f))
        Button(
            onClick = { navController.navigate(Ejercicio3) }
        ) { Text("Ejercicio 3") }
        Spacer(modifier = Modifier.weight(1f))
    }
}