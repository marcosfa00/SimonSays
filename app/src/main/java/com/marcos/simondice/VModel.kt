package com.marcos.simondice

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VModel : ViewModel() {

    //declaramos TAG_LOG
    private val tag = "corutina"


    fun startGame() {
        Log.d(tag, "Iniciando juego")
        Data.round.value = 0
        Data.secuence = mutableListOf()
        Data.secuenceUser = mutableListOf()
        Data.state = Data.State.START
    }



    fun changeState() {
        Log.d(tag, "Cambia el estado de la aplicación")

        Data.state = when (Data.state) {
            Data.State.START -> Data.State.SEQUENCE
            Data.State.SEQUENCE -> Data.State.WAITING
            Data.State.WAITING -> Data.State.CHECKING
            Data.State.CHECKING -> Data.State.FINISHED
            Data.State.FINISHED -> Data.State.START

        }

        getState() // Llamar a la función getState()
    }

    /**
     * FUNCION getState
     */
    private fun getState(): Data.State {
        Log.d(tag, "El estado actual es: ${Data.state}")
        return Data.state

    }

    /**
     * FUNCION que genera un numero aleatorio entre 0 y un numero menor del máximo.

     * @return Int: Número aleatorio generado
     */
    private fun generarNumeroAleatorio(): Int {
        return (0..3).random()

    }

    @SuppressLint("SuspiciousIndentation")
    fun cambiaColorBotonAlPulsar(color: MutableState<Color>) {
        Log.d(tag, "Cambia el color del boton al pulsar")
        viewModelScope.launch {
            Data.colorPath = color.value
            color.value = darkestColor(Data.colorPath)
            delay(300)
            color.value = Data.colorPath
        }
    }
    /**
     * Función que muestra la secuencia de colores más oscuros.
     */
    private fun darkestColor(color: Color): Color {
        val r = (color.red * (1 - 0.5f)).coerceIn(0f, 1f)
        val g = (color.green * (1 -  0.5f)).coerceIn(0f, 1f)
        val b = (color.blue * (1 -  0.5f)).coerceIn(0f, 1f)
        return Color(r, g, b, color.alpha)
    }

    //Funcion para generar una secuencia:
    // Variable para habilitar/deshabilitar botones
    var buttonsEnabled by mutableStateOf(true)

    // Función para desactivar los botones
    private fun disableButtons() {
        buttonsEnabled = false
    }

    // Función para habilitar los botones
    private fun enableButtons() {
        buttonsEnabled = true
    }

    // Función para generar una secuencia
    fun generarSecuencia() {
        Log.d(tag, "Generar Secuencia")
        Log.d(tag, "Estado actual: ${Data.state}")
        Data.secuence.add(generarNumeroAleatorio())
        Log.d(tag, "Secuencia generada: ${Data.secuence}")


        // Desactivar los botones durante la visualización de la secuencia
        disableButtons()

        viewModelScope.launch {

            for (i in Data.secuence) {
                Log.d(tag, "Mostramos el color $i")
                Data.colorPath = Data.numColors[i].color.value
                Data.numColors[i].color.value = darkestColor(Data.colorPath)
                delay(400)
                Data.numColors[i].color.value = Data.colorPath
                delay(400)
            }

            // Habilitar los botones después de mostrar la secuencia completa
            enableButtons()
        }
    }
    /**
     * Ahora debemso hacer una función que aumente la secuencia del usuario para
     * que cada vez se muestren más colores
     */
    fun aumentarSecuencia(){
        if (Data.state == Data.State.SEQUENCE){
            generarSecuencia()
            Data.state = Data.State.WAITING
            Log.d(tag, "Cambia el estado a ${Data.state}")

            //Ahora reseteamos la secuencia del usuario
            Data.secuenceUser = mutableListOf()

        }
    }



    /**
     * Fuyncion que muestra colores claros
     */
    /* private fun lightestColor(color: Color): Color {
         val r = (color.red + (1 - color.red) *  0.5f).coerceIn(0f, 1f)
         val g = (color.green + (1 - color.green) *  0.5f).coerceIn(0f, 1f)
         val b = (color.blue + (1 - color.blue) *  0.5f).coerceIn(0f, 1f)
         return Color(r, g, b, color.alpha)
     }*/


    fun guardarSecuenciaUsuario(color: Int) {
        Data.secuenceUser.add(color)
        Log.d(tag, "Secuencia del usuario: ${Data.secuenceUser}")

    }



    /**
     * Función que comprueba si la secuencia del usuario es correcta
     */
    /**
     * Función que comprueba si la secuencia del usuario es correcta
     */
    fun comprobarSecuencia(): Boolean {
        val correcta : Boolean
        Data.state = Data.State.CHECKING
        Log.d(tag, "Cambiamso el estado a ${Data.state}")
        if (Data.secuence == Data.secuenceUser) {
            Log.d(tag, "OK")
            Data.state = Data.State.SEQUENCE//Cambiamos el estado a SEQUENCE
            correcta = true
            Data.round.value++

            if (Data.round.value > Data.record.value) {
                Data.record.value = Data.round.value
            }
        } else {
            Log.d(tag, "NOT OK")
            correcta = false
            Data.state = Data.State.FINISHED
            Log.d(tag, "Se cambia el Estado de la aplicación a${Data.state}")
            Data.round.value = 0
        }
        return correcta
    }


}