package com.marcos.simondice

import Greeting
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.marcos.simondice.ui.theme.SimonDiceTheme
import android.media.MediaPlayer
// Define una interfaz para controlar la reproducción del audio
class MainActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializar MediaPlayer con el sonido de fondo
        mediaPlayer = MediaPlayer.create(this, R.raw.melendi_lagrimas_desordenadas)
        mediaPlayer.isLooping = true // Repetir el sonido en bucle
        mediaPlayer.start() // Iniciar la reproducción del sonido
        setContent {
            SimonDiceTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    Greeting(miModel = VModel())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release() // Liberar recursos al destruir la actividad
    }


}




