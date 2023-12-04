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

    /**
     * Inicia un nuevo juego, reiniciando los valores del juego a su estado inicial.
     * Establece el número de rondas en cero, reinicia las secuencias del juego
     * y establece el estado del juego como "START".
     */
    fun startGame() {
        Log.d(tag, "Iniciando juego")
        Data.round.value = 0
        Data.secuence = mutableListOf()
        Data.secuenceUser = mutableListOf()
        Data.state = Data.State.START
    }


    /**
     * Cambia el estado de la aplicación al siguiente estado en el ciclo del juego.
     * Los posibles estados son: START, SEQUENCE, WAITING, CHECKING y FINISHED.
     * Dependiendo del estado actual, cambia al siguiente estado en el ciclo del juego.
     * Llama a la función getState() después de cambiar el estado.
     */
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
     * Obtiene y registra el estado actual de la aplicación.
     * Imprime en el registro el estado actual de la aplicación y devuelve dicho estado.
     *
     * @return El estado actual de la aplicación.
     */
    private fun getState(): Data.State {
        Log.d(tag, "El estado actual es: ${Data.state}")
        return Data.state

    }

    /**
     * Genera y devuelve un número aleatorio entre 0 y 3, ambos inclusive.
     *
     * @return El número aleatorio generado.
     */
    private fun generarNumeroAleatorio(): Int {
        return (0..3).random()

    }

    /**
     * Cambia el color del botón al ser pulsado.
     * Utiliza un efecto de transición para cambiar el color del botón al color más oscuro
     * durante un breve lapso de tiempo y luego vuelve al color original.
     *
     * @param color El estado mutable del color del botón.
     */
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
     * Obtiene el tono más oscuro de un color dado, reduciendo su brillo en un 50%.
     *
     * @param color El color del que se va a obtener el tono más oscuro.
     * @return El color resultante con un tono más oscuro.
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

    /**
     * Genera una secuencia aleatoria de colores en el juego Simon.
     * Agrega un número aleatorio a la secuencia y muestra secuencialmente los colores
     * almacenados en la secuencia, dando una breve pausa entre cada color.
     * Desactiva los botones durante la visualización de la secuencia y luego los habilita.
     */
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
     * Aumenta la secuencia de colores en el juego Simon.
     * Si el estado actual es "SEQUENCE", genera una nueva secuencia aleatoria de colores
     * y cambia el estado a "WAITING". También resetea la secuencia del usuario.
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
     * Guarda el color seleccionado por el usuario en la secuencia del jugador.
     *
     * @param color El índice del color seleccionado por el usuario.
     */
    fun guardarSecuenciaUsuario(color: Int) {
        Data.secuenceUser.add(color)
        Log.d(tag, "Secuencia del usuario: ${Data.secuenceUser}")

    }




    /**
     * Comprueba si la secuencia del jugador coincide con la secuencia generada por el juego.
     * Verifica si la secuencia del jugador es igual a la secuencia generada por el juego.
     *
     * @return Devuelve true si la secuencia del jugador coincide con la secuencia generada por el juego, de lo contrario, devuelve false.
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