package com.cmj.seminarionotificaciones.core.navigation.screens

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.app.NotificationManagerCompat
import com.cmj.seminarionotificaciones.MainActivity
import com.cmj.seminarionotificaciones.MainActivity.Companion.CHANNEL_ID
import com.cmj.seminarionotificaciones.MainActivity.Companion.id
import com.cmj.seminarionotificaciones.R

@Composable
fun Ejercicio1(modifier: Modifier = Modifier, contexto: Context) {
    val intent = Intent(contexto, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent = PendingIntent.getActivity(
        contexto,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val actionIntent = Intent(contexto, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    actionIntent.putExtra("actividad", "Ejercicio1")

    val actionPendingIntent = PendingIntent.getActivity(
        contexto,
        0,
        actionIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(contexto, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("¿Hola qué tal?")
        .setContentText("Pasa para saludarte desde esta notificación. Mi id es $id")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .addAction(R.drawable.ic_launcher_foreground, "Ir a la actividad", actionPendingIntent)
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
                    notify(id.incrementAndGet(), builder.build())
                }
            }
        ) {
            Text("Crear notificación")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}