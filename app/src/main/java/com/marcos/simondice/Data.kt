package com.marcos.simondice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

    /**
     * Arreglo de colores disponibles en el juego Simon.
     * Contiene todos los colores definidos en la enumeración Colors.
     */
    var numColors = Colors.values()

    /**
     * Color actual utilizado en el juego.
     * Almacena el color actual en el camino del juego.
     * Por defecto, se establece en blanco.
     */
    var colorPath: Color = Color.White


    /**
     * Enumeración que representa los distintos estados del juego.
     * Los estados incluyen START, SEQUENCE, WAITING, CHECKING y FINISHED.
     */
    enum class State {
        START, // Estado inicial del juego
        SEQUENCE, // Mostrando la secuencia de colores
        WAITING, // Esperando la interacción del usuario
        CHECKING, // Comprobando la secuencia del usuario
        FINISHED // Juego finalizado
    }


    /**
     * Enumeración que representa los colores del juego Simon.
     * Cada color tiene asociado un estado mutable y un nombre.
     *
     * @property color El estado mutable del color.
     * @property colorName El nombre del color.
     */
    enum class Colors(val color: MutableState<Color>,  val colorName: String) {
        ROJO(mutableStateOf(Color.Red), "ROJO"),
        AZUL(color = mutableStateOf(Color.Blue), "AZUL"),
        AMARILLO(color = mutableStateOf(Color.Yellow), "AMARILLO"),
        VERDE(color = mutableStateOf(Color.Green), "VERDE"),

    }


}