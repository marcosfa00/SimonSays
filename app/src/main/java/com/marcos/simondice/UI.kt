import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun Greeting(miModel: VModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp)) // Añadir espacio superior
        Ronda(miModel = miModel) // Mostrar el componente Ronda aquí
        Botonera(vModel = miModel)
        Spacer(modifier = Modifier.height(16.dp)) // Añadir espacio entre la Botonera y los botones
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            startButton(miModel = miModel)
            Spacer(modifier = Modifier.width(16.dp)) // Espacio entre botones
            enviar(miModel = miModel)
        }
    }
}


@Composable
fun Ronda(miModel: VModel) {
    Text(
        text = "RONDA: ${Data.round.value} ", // Mostrar el número de ronda
        color = Color.Black,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 40.sp
    )
    // Puedes agregar aquí cualquier otra lógica o contenido relacionado con la ronda si es necesario
}


/**
 * Función que coge la lista de los 4 vcolores, la divide en dos, y crea 4 botones
 * en forma de cuadrado
 */
@Composable
fun Botonera(vModel: VModel) {
    val colorsInTwoRows = Data.Colors.values().toList().chunked(2)

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        colorsInTwoRows.forEach { rowColors ->
            Row {
                rowColors.forEach { color ->
                    Spacer(modifier = Modifier.width(8.dp))
                    Boton(color = color.color, miModel = vModel, name = color.colorName)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

/**
 * Ahora vamos a crear el boton Start
 */
@Composable
fun startButton(miModel: VModel) {
    //Declaramos un Boton
    Button(
        onClick = {
                miModel.startGame()
                miModel.changeState()
                if (Data.state == Data.State.SEQUENCE){
                    miModel.generarSecuencia()
                    miModel.changeState()
                }else{
                    miModel.startGame()
                }




        },
        modifier = Modifier
           // Aumentar ligeramente el tamaño del botón
    ) {
        Text(
            text = "START",
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}

@Composable
fun enviar(miModel: VModel) {
    Button(
        onClick = {
            if (Data.state == Data.State.WAITING){
               if ( miModel.comprobarSecuencia()) {
                    miModel.aumentarSecuencia()
               }else{
                     miModel.changeState()
               }

            }


        },
        modifier = Modifier
            .padding(horizontal = 16.dp) // Espacio a los lados del botón
           // Aumentar ligeramente el tamaño del botón
    ) {
        Text(
            text = "ENVIAR",
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}


@Composable
fun Boton(color: MutableState<Color>, miModel: VModel, name: String) {
    Button(
        onClick = {
                  //Recogemos el color que hemos pulsado
                   // miModel.aumentarSecuenciaUsuario(Data.colors.indexOf(color))
            if (Data.state == Data.State.WAITING){
                miModel.guardarSecuenciaUsuario(Data.colors.indexOf(color))

            }



        },
        modifier = Modifier
            .padding(10.dp)
            .size(150.dp),
        colors = ButtonDefaults.buttonColors(color.value)
    ) {
        Text(
            text = name,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}






