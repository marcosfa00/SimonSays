import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marcos.simondice.Data
import com.marcos.simondice.VModel

@Composable
fun Greeting(miModel: VModel){
    //Title(miModel = miModel)

        Ronda(miModel = miModel)
        Botonera(vModel = miModel)
        Row {

            startButton(miModel = miModel)
            enviar(miModel = miModel)
        }
    if (miModel.showGameOverDialog) {
        GameOverDialog(miModel = miModel)
    }



}

@Composable
fun GameOverDialog(miModel: VModel) {
    AlertDialog(
        onDismissRequest = {
            miModel.showGameOverDialog = false
        },
        title = {
            Text(text = "Game Over")
        },
        text = {
            Text(text = "¡Has cometido un error! Reiniciando el juego.")
        },
        confirmButton = {
            Button(
                onClick = {
                    miModel.showGameOverDialog = false
                    // Aquí puedes realizar cualquier otra acción necesaria antes de reiniciar el juego.
                }
            ) {
                Text("Aceptar")
            }
        }
    )
}

@Composable
fun Title(miModel: VModel) {
    val colorAnimation = rememberInfiniteTransition()



    val color by colorAnimation.animateColor(
        initialValue = Data.colorsTitulo.first(),
        targetValue = Data.colorsTitulo.last(),
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {



        Text(
            text = "SIMON DICE",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = color
            ),
            modifier = Modifier

                .wrapContentSize()
        )


    }
}
@Composable
fun Ronda(miModel: VModel) {
    val colorAnimation = rememberInfiniteTransition()



    val color by colorAnimation.animateColor(
        initialValue = Data.colorsTitulo.first(),
        targetValue = Data.colorsTitulo.last(),
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {



        Text(
            text = "RONDA: ${miModel.getRonda()} ",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = color
            ),
            modifier = Modifier

                .wrapContentSize()
        )


    }
}

/**
 * Función que coge la lista de los 4 vcolores, la divide en dos, y crea 4 botones
 * en forma de cuadrado
 */
@Composable
fun Botonera(vModel: VModel) {
    val colorsInTwoRows = Data.Colors.values().toList().chunked(2)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            colorsInTwoRows.forEach { rowColors ->
                Row {
                    rowColors.forEach { color ->
                        Spacer(modifier = Modifier.width(8.dp))
                        Boton(color = color, miModel = vModel)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
/**
 * Ahora vamos a crear el boton Start
 */
@Composable
fun startButton(miModel: VModel){

    //Declaramos un Boton
    Button(
        onClick = {
        //Bien lo que debemos hacer aquí, es cambiar el estado del juego
            miModel.inicializarJuego()
            miModel.mostrarSecuencia()
            miModel.changeStatus()

        }
    ){
        Text(
            text = miModel.getStatus(),
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}

@Composable
fun enviar(miModel: VModel){
    Button(
        onClick = {
            if (miModel.getStatus()== "START") {
                //NOTHING CHANGE
            }else{
               miModel.comprobarSecuencia()
                    //Nothing change


            }

        },
                modifier = Modifier.padding(horizontal = 16.dp) // Espacio a los lados del botón
    ){
        Text(
            text = "ENVIAR",
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}


@Composable
fun Boton(color: Data.Colors, miModel: VModel) {
    Button(
        onClick = {
            if (Data.state != Data.State.SEQUENCE) {
                miModel.aumentarSecuenciaUsuario(color = Data.colors.indexOf(color.color))

            }
                  },
        modifier = Modifier
            .padding(10.dp)
            .size(150.dp),
        colors = ButtonDefaults.buttonColors(color.color.value)
    ) {
        Text(
            text = color.colorName,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}



