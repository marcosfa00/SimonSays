package com.marcos.simondice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

object Data {

    var round = mutableStateOf(0);
    var secuence = mutableListOf<Int>();
    var secuenceUser = mutableListOf<Int>();
    var record = mutableStateOf(0);
    var state = State.START;
    var colors = listOf(
        Colors.ROJO.color,
        Colors.AZUL.color,
        Colors.AMARILLO.color,
        Colors.VERDE.color
    )

    //Aun no se para que usar esto
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