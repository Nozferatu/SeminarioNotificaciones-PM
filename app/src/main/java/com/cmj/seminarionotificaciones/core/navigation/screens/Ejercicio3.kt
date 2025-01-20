package com.cmj.seminarionotificaciones.core.navigation.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigPictureStyle
import androidx.core.app.NotificationManagerCompat
import coil.compose.AsyncImage
import com.cmj.seminarionotificaciones.MainActivity.Companion.CHANNEL_ID
import com.cmj.seminarionotificaciones.MainActivity.Companion.id
import com.cmj.seminarionotificaciones.R

@Composable
fun Ejercicio3(modifier: Modifier = Modifier, contexto: Context) {
    val scrollState = rememberScrollState()

    val builder = NotificationCompat.Builder(contexto, CHANNEL_ID)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    val modifierInput = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)

    val iconos = listOf("Icono 1", "Icono 2")

    var titulo by rememberSaveable { mutableStateOf("") }
    var contenido by rememberSaveable { mutableStateOf("") }
    var iconoElegido by rememberSaveable { mutableStateOf("") }

    val cantidadBotones = remember { mutableIntStateOf(1) }
    var nombreBotones by rememberSaveable { mutableStateOf("") }

    var uriImagen by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        uriImagen = uri
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp, horizontal = 20.dp)
            .verticalScroll(scrollState),
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
            value = contenido,
            onValueChange = { contenido = it },
            label = { Text("Contenido") }
        )

        Spinner("Selecciona el icono", iconos){
            iconoElegido = it.toString()
        }

        Text(modifier = Modifier.padding(vertical = 10.dp),
            text = "Número de botones"
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 20.dp),
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
                onClick = {
                    if(cantidadBotones.intValue < 3) cantidadBotones.intValue++
                }
            ) { Icon(imageVector = Icons.Default.Add, "") }
        }

        OutlinedTextField(
            modifier = modifierInput,
            value = nombreBotones,
            onValueChange = { nombreBotones = it },
            label = { Text("Nombre de los botones") }
        )

        AsyncImage(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .size(100.dp),
            model = uriImagen,
            contentScale = ContentScale.Crop,
            contentDescription = "Imagen de la notificación"
        )

        Button(modifier = Modifier.padding(vertical = 5.dp),
            onClick = {
                launcher.launch("image/*")
            }
        ) { Text("Escoger imagen") }

        Button(modifier = Modifier.padding(vertical = 5.dp),
            onClick = {
                if(iconoElegido.isNotEmpty()){
                    with(NotificationManagerCompat.from(contexto)) {
                        if (ActivityCompat.checkSelfPermission(
                                contexto,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return@Button
                        }

                        builder.apply {
                            val resourceIcono = when(iconoElegido){
                                "Icono 1" -> R.drawable.icono_1
                                "Icono 2" -> R.drawable.icono_2
                                else -> 0
                            }

                            setSmallIcon(resourceIcono)
                            setContentTitle(titulo)
                            setContentText(contenido)
                            setStyle(
                                BigPictureStyle().bigPicture(
                                MediaStore.Images.Media.getBitmap(contexto.contentResolver, uriImagen)
                            ))

                            if(cantidadBotones.intValue > 0){
                                val nombreBotonesList = nombreBotones.split(",")
                                for(i in 1..cantidadBotones.intValue){
                                    addAction(
                                        R.drawable.ic_launcher_foreground,
                                        nombreBotonesList.getOrNull(i) ?: "Nada",
                                        null
                                    )
                                }
                            }

                        }

                        notify(id.incrementAndGet(), builder.build())
                    }
                }else{
                    Toast.makeText(
                        contexto,
                        "Tiene que elegir el icono",
                        Toast.LENGTH_SHORT).show()
                }
            }
        ) { Text("Enviar notificación") }
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