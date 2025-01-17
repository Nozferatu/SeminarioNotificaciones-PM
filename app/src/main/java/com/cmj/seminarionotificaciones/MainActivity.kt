package com.cmj.seminarionotificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cmj.seminarionotificaciones.core.navigation.NavigationWrapper
import com.cmj.seminarionotificaciones.ui.theme.SeminarioNotificacionesPMTheme
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : ComponentActivity() {
    private lateinit var actividadIntent: String

    companion object {
        val APP_ID = "com.cmj.seminarionotificaciones"
        val CHANNEL_ID = "${APP_ID}_69"
        var id = AtomicInteger(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        createNotificationChannel()

        actividadIntent = intent.getStringExtra("actividad") ?: ""

        setContent {
            SeminarioNotificacionesPMTheme {
                NavigationWrapper(actividadIntent)
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
