package com.cmj.seminarionotificaciones.core.navigation.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import coil.compose.AsyncImage
import com.cmj.seminarionotificaciones.MainActivity.Companion.CHANNEL_ID
import com.cmj.seminarionotificaciones.R

@Composable
fun Ejercicio3(modifier: Modifier = Modifier, contexto: Context) {
    val builder = NotificationCompat.Builder(contexto, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(R.drawable.ic_launcher_foreground, "Compartir", null)
        .addAction(R.drawable.ic_launcher_foreground, "Borrar", null)
        .setAutoCancel(true)

    val modifierInput = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)

    val iconos = listOf("Icono 1", "Icono 2")

    var titulo by rememberSaveable { mutableStateOf("") }
    var texto by rememberSaveable { mutableStateOf("") }
    var iconoElegido by rememberSaveable { mutableStateOf("") }

    var cantidadBotones = remember { mutableIntStateOf(1) }

    var uriImagen by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        uriImagen = uri
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = modifierInput,
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") }
        )

        OutlinedTextField(
            modifier = modifierInput,
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Texto") }
        )

        Spinner("Selecciona el icono", iconos){
            iconoElegido = it.toString()
        }

        Text(modifier = Modifier.padding(vertical = 10.dp),
            text = "Número de botones"
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically

        ){
            Button(
                onClick = {
                    if(cantidadBotones.intValue > 0) cantidadBotones.intValue--
                }
            ) { Icon(painterResource(R.drawable.baseline_remove_24), "") }

            Text(cantidadBotones.intValue.toString(), fontSize = 20.sp)

            Button(
                onClick = { cantidadBotones.intValue++ }
            ) { Icon(imageVector = Icons.Default.Add, "") }
        }

        AsyncImage(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .size(100.dp),
            model = uriImagen,
            contentScale = ContentScale.Crop,
            contentDescription = "Imagen de la notificación"
        )

        Button(modifier = Modifier.padding(vertical = 20.dp),
            onClick = {
                launcher.launch("image/*")
            }
        ) { Text("Escoger imagen") }
    }
}

@Composable
fun Spinner(
    textoPlaceholder: String = "Sin elegir",
    listaOpciones: List<Any>,
    onSelected: (opcion: Any) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    var eleccion by rememberSaveable { mutableStateOf("") }

    Box {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            value = eleccion,
            onValueChange = {},
            label = { Text(textoPlaceholder) },
            trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
            readOnly = true,
            shape = RoundedCornerShape(6.dp)
        )

        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listaOpciones.forEach { opcion ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        eleccion = opcion.toString()
                        onSelected(opcion)
                    },
                    text = { Text(opcion.toString()) }
                )
            }
        }

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded = !expanded }
                )
        )
    }
}