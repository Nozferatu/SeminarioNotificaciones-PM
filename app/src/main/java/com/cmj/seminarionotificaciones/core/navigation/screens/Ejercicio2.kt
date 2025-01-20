package com.cmj.seminarionotificaciones.core.navigation.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigPictureStyle
import androidx.core.app.NotificationCompat.BigTextStyle
import androidx.core.app.NotificationManagerCompat
import com.cmj.seminarionotificaciones.MainActivity.Companion.CHANNEL_ID
import com.cmj.seminarionotificaciones.MainActivity.Companion.id
import com.cmj.seminarionotificaciones.R

@Composable
fun Ejercicio2(modifier: Modifier = Modifier, contexto: Context) {
    val builder = NotificationCompat.Builder(contexto, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(R.drawable.ic_launcher_foreground, "Compartir", null)
        .addAction(R.drawable.ic_launcher_foreground, "Borrar", null)
        .setAutoCancel(true)

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 30.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                with(NotificationManagerCompat.from(contexto)){
                    if (ActivityCompat.checkSelfPermission(
                            contexto,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return@Button
                    }

                    builder.apply {
                        setContentTitle("Captura de pantalla realizada")
                        setContentText("Pulsa para ver la captura de pantalla")
                        setStyle(BigPictureStyle().bigPicture(
                            BitmapFactory.decodeResource(contexto.resources, R.drawable.imagen_grande)
                        ))
                    }

                    notify(id.incrementAndGet(), builder.build())
                }
            }
        ) {
            Text("Notificación BigPictureStyle")
        }

        Button(
            onClick = {
                with(NotificationManagerCompat.from(contexto)){
                    if (ActivityCompat.checkSelfPermission(
                            contexto,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return@Button
                    }

                    builder.apply {
                        setContentTitle("Extenso relato")
                        //setContentText("Pulsa para ver la captura de pantalla")
                        setStyle(
                            BigTextStyle().bigText(contexto.getString(R.string.extenso_relato))
                        )
                    }

                    notify(id.incrementAndGet(), builder.build())
                }
            }
        ) {
            Text("Notificación BigTextStyle")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}