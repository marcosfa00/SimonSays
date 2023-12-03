package com.marcos.simondice

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.DisposableEffect
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
    val TAG_LOG = "corutina"


    fun startGame() {
        Log.d(TAG_LOG, "Iniciando juego")
        Data.round.value = 0
        Data.secuence = mutableListOf<Int>()
        Data.secuenceUser = mutableListOf<Int>()
        Data.state = Data.State.START
    }


   /* fun changeState() {
        Log.d(TAG_LOG, "Cambia el estado de la aplicación")
        if (Data.state == Data.State.START) {
            Data.state = Data.State.SEQUENCE
            getState()
        } else if (Data.state == Data.State.SEQUENCE) {
            Data.state = Data.State.WAITING
            getState()
        } else if (Data.state == Data.State.WAITING) {
            Data.state = Data.State.INPUT
            getState()
        } else if (Data.state == Data.State.INPUT) {
            Data.state = Data.State.CHECKING
            getState()
        } else if (Data.state == Data.State.CHECKING) {
            Data.state = Data.State.FINISHED
            getState()
        } else if (Data.state == Data.State.FINISHED) {
            Data.state = Data.State.START
            getState()
        }

    }*/
   fun changeState() {
       Log.d(TAG_LOG, "Cambia el estado de la aplicación")

       Data.state = when (Data.state) {
           Data.State.START -> Data.State.SEQUENCE
           Data.State.SEQUENCE -> Data.State.WAITING
           Data.State.WAITING -> Data.State.CHECKING
           Data.State.CHECKING -> Data.State.FINISHED
           Data.State.FINISHED -> Data.State.START
           else -> Data.state // Manejo por defecto si no coincide con ninguno de los casos
       }

       getState() // Llamar a la función getState()
   }

    /**
     * FUNCION getState
     */
    fun getState(): Data.State {
        Log.d(TAG_LOG, "El estado actual es: ${Data.state}")
        return Data.state

    }

    /**
     * FUNCION que genera un numero aleatorio entre 0 y un numero menor del máximo.
     * @param maximo: Número máximo que se puede generar
     * @return Int: Número aleatorio generado
     */
    fun generarNumeroAleatorio(maximo: Int): Int {
        return (0..maximo).random()

    }

@SuppressLint("SuspiciousIndentation")
fun cambiaColorBotonAlPulsar(color: MutableState<Color>) {
        Log.d(TAG_LOG, "Cambia el color del boton al pulsar")
       viewModelScope.launch {
           Data.colorPath = color.value
           color.value = darkestColor(Data.colorPath, 0.5f)
              delay(300)
                color.value = Data.colorPath
       }
    }

    //Funcion para generar una secuencia:
    // Variable para habilitar/deshabilitar botones
    var buttonsEnabled by mutableStateOf(true)

    // Función para desactivar los botones
    fun disableButtons() {
        buttonsEnabled = false
    }

    // Función para habilitar los botones
    fun enableButtons() {
        buttonsEnabled = true
    }

    // Función para generar una secuencia
    fun generarSecuencia() {
        Log.d(TAG_LOG, "Generando secuencia")
        Data.secuence.add(generarNumeroAleatorio(4))
        Log.d(TAG_LOG, "Secuencia generada: ${Data.secuence}")

        // Desactivar los botones durante la visualización de la secuencia
        disableButtons()

        viewModelScope.launch {
            for (i in Data.secuence) {
                Log.d(TAG_LOG, "Mostramos el color $i")
                Data.colorPath = Data.numColors[i].color.value
                Data.numColors[i].color.value = lightestColor(Data.colorPath, 0.5f)
                delay(400)
                Data.numColors[i].color.value = Data.colorPath
                delay(400)
            }

            // Habilitar los botones después de mostrar la secuencia completa
            enableButtons()
        }
    }

    /**
     * Función que muestra la secuencia de colores más oscuros.
     */
    fun darkestColor(color: Color, factor: Float): Color {
        val r = (color.red * (1 - factor)).coerceIn(0f, 1f)
        val g = (color.green * (1 - factor)).coerceIn(0f, 1f)
        val b = (color.blue * (1 - factor)).coerceIn(0f, 1f)
        return Color(r, g, b, color.alpha)
    }

    /**
     * Fuyncion que muestra colores claros
     */
    fun lightestColor(color: Color, factor: Float): Color {
        val r = (color.red + (1 - color.red) * factor).coerceIn(0f, 1f)
        val g = (color.green + (1 - color.green) * factor).coerceIn(0f, 1f)
        val b = (color.blue + (1 - color.blue) * factor).coerceIn(0f, 1f)
        return Color(r, g, b, color.alpha)
    }


    fun guardarSecuenciaUsuario(color: Int) {
        Data.secuenceUser.add(color)
        Log.d(TAG_LOG, "Secuencia del usuario: ${Data.secuenceUser}")

    }



    /**
     * Función que comprueba si la secuencia del usuario es correcta
     */
    /**
     * Función que comprueba si la secuencia del usuario es correcta
     */
    fun comprobarSecuencia(): Boolean {
        var correcta : Boolean
        Data.state = Data.State.CHECKING
        Log.d(TAG_LOG, "Cambiamso el estado a ${Data.state}")
        if (Data.secuence == Data.secuenceUser) {
            Log.d(TAG_LOG, "Secuencia correcta")
            Data.state = Data.State.SEQUENCE//Cambiamos el estado a SEQUENCE
            correcta = true
            Data.round.value++

            if (Data.round.value > Data.record.value) {
                Data.record.value = Data.round.value
            }
        } else {
            Log.d(TAG_LOG, "Secuencia incorrecta")
            correcta = false
            Data.state = Data.State.FINISHED
            Log.d(TAG_LOG, "Se cambia el Estado de la aplicación a${Data.state}")
            Data.round.value = 0
        }
        return correcta
    }

    /**
     * Ahora debemso hacer una función que aumente la secuencia del usuario para
     * que cada vez se muestren más colores
     */
   fun aumentarSecuencia(){
      if (Data.state == Data.State.SEQUENCE){
          generarSecuencia()
          Data.state = Data.State.WAITING

          //Ahora reseteamos la secuencia del usuario
            Data.secuenceUser = mutableListOf<Int>()

      }
   }
}










































