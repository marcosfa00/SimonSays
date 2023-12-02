package com.marcos.simondice

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class VModel : ViewModel() {



    var showGameOverDialog by mutableStateOf(false)

    val TAG = "Corrutina"

    /**
     * Función que genera un número aleatorio entre 0 y el máximo que se le pasa
     */
    fun generarNumeroAleatorio(maximo: Int): Int {
        return (0..maximo).random()
    }

    /**
     * Inicializa el juego
     */
    fun inicializarJuego() {
        showGameOverDialog = false
        Log.d(TAG, "inicializarJuego")
        reiniciarRonda()
        reiniciarSecuencia()
        reiniciarSecuenciaUsuario()
        Data.state = Data.State.START
    }

    /*
    * reiniciar Ronda
     */
    fun reiniciarRonda() {
        Data.round.value = 0
    }

    fun mostrarDialogoGameOver() {
        showGameOverDialog = true
        // Puedes agregar más lógica aquí si es necesario al mostrar el diálogo
    }

    /*
    * reiniciar Secuencia
     */
    fun reiniciarSecuencia() {
        Data.secuence.clear()
    }

    /*
    Reiniciar Secuencia Usuario
     */
    fun reiniciarSecuenciaUsuario() {
        Data.secuenceUser.clear()
    }

    /**
     * Aumenta la secuencia de colores
     */
    fun mostrarSecuencia() {
        //Cambiamos el estado del juego a SECUENCIA
        Log.d(TAG, "mostrarSecuencia")
        //Ahora debemos crear una especie de hilo, una corrutina, que mostrara la secuencia
        //Para ello, esta clase ViewModel debera eredar de ViewModel
        viewModelScope.launch {
            //Recorremos la secuencia
            for (i in Data.secuence) {
                Log.d(TAG, "mostrarSecuencia (Color): $i")
                Data.colorPath = Data.numColors[i].color.value
                Data.numColors[i].color.value = darkestColor(Data.colorPath,0.5f)
                delay(500)
                Data.numColors[i].color.value = Data.colorPath
                delay(500)
            }
            Data.state = Data.State.WAITING
            Log.d(TAG, Data.state.toString())//mostramos el estado en el Log cat
        }
        Log.d(TAG, Data.state.toString())//mostramos el estado en el Log cat


    }

    /**
     * Darkest the color of the button
     */
    fun darkestColor(color: Color, factor: Float): Color {
        val r = (color.red * (1 - factor)).coerceIn(0f, 1f)
        val g = (color.green * (1 - factor)).coerceIn(0f, 1f)
        val b = (color.blue * (1 - factor)).coerceIn(0f, 1f)
        return Color(r, g, b, color.alpha)
    }


    /**
     * Aumenta un color a la secuencia
     */
    fun aumentarSecuencia() {
        Data.state = Data.State.SEQUENCE
        Data.secuence.add(generarNumeroAleatorio(4))
        mostrarSecuencia()
    }

    /**
     * Aumentar la secuencia del usuario.
     */
    fun aumentarSecuenciaUsuario(color: Int) {
        Data.state = Data.State.INPUT
        Data.secuenceUser.add(color)
        Log.d(TAG, Data.secuenceUser.toString())

    }

    fun changeStatus() {
        if (Data.playStatus.value == "START") {
            Data.playStatus.value= "RESTART"
            //Execute the first round
            Data.round.value++
            aumentarSecuencia()
        } else {
            Data.playStatus.value= "START"
            reiniciarRonda()
        }
    }
    fun getStatus(): String {
        return Data.playStatus.value
    }


    /**
     * Comprueba la secuencia del usuario
     */
    fun comprobarSecuencia(): Boolean {
        var ok =false
        Data.state = Data.State.CHECKING
        if (Data.secuence == Data.secuenceUser) {

            if (Data.round.value > Data.record.value) {
                Data.record.value = Data.round.value
            }
            reiniciarSecuenciaUsuario()
            aumentarSecuencia()
            Data.round.value++
            ok = true
        } else if (Data.secuenceUser.size != Data.secuence.size) {
            showGameOverDialog = true // Mostrar el diálogo de Game Over
            ok = false
            inicializarJuego() // Reiniciar el juego después del Game Over
            Data.state = Data.State.FINISHED

            ok= false
        }
        return ok
    }


    fun getRonda(): Int {
        return Data.round.value
    }
}


















