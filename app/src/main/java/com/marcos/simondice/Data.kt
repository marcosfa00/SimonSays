package com.marcos.simondice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

object Data {

    var round = mutableStateOf(0);
    var secuence = mutableListOf<Int>();
    var playStatus = mutableStateOf("START")
    var secuenceUser = mutableListOf<Int>();
    var record = mutableStateOf(0);
    var state = State.START;
    var colors = listOf(
        Colors.ROJO.color,
        Colors.AZUL.color,
        Colors.AMARILLO.color,
        Colors.VERDE.color
    )
    val colorsTitulo = listOf(
        Color(0xFF8C34D8), // Púrpura oscuro
        Color(0xFFE65100), // Naranja oscuro
        Color(0xFF004D40), // Verde oscuro
        Color(0xFFC2185B), // Rosa oscuro
        Color(0xFF1A237E), // Azul oscuro
        Color(0xFFD50000), // Rojo oscuro
    )

    var numColors = Colors.values()
    var colorPath: Color = Color.White


    /**
     * Enum que representa los estados del juego
     */
    enum class State {
        START, SEQUENCE, WAITING, INPUT, CHECKING, FINISHED
    }

    /**
     * Enum para los colores del juego
     */
    enum class Colors(val color: MutableState<Color>,  val colorName: String) {
        ROJO(mutableStateOf(Color.Red), "ROJO"),
        AZUL(color = mutableStateOf(Color.Blue), "AZUL"),
        AMARILLO(color = mutableStateOf(Color.Yellow), "AMARILLO"),
        VERDE(color = mutableStateOf(Color.Green), "VERDE"),

    }








}