package com.cmj.seminarionotificaciones

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.cmj.seminarionotificaciones.MainActivity.Companion.CHANNEL_ID
import com.cmj.seminarionotificaciones.MainActivity.Companion.id
import com.cmj.seminarionotificaciones.ui.theme.SeminarioNotificacionesPMTheme
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : ComponentActivity() {
    companion object {
        val APP_ID = "com.cmj.seminarionotificaciones"
        val CHANNEL_ID = "${APP_ID}_69"
        var id = AtomicInteger(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        createNotificationChannel()

        setContent {
            SeminarioNotificacionesPMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Inicio(
                        modifier = Modifier.padding(innerPadding),
                        applicationContext
                    )
                }
            }
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "canal_fachero"
            val descriptionText = "Canal de notificaciones"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

@Composable
fun Inicio(modifier: Modifier = Modifier, contexto: Context) {
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
        .padding(vertical = 10.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
    }
}