package com.marcos.simondice

import Greeting
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.marcos.simondice.ui.theme.SimonDiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimonDiceTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    Greeting(miModel = VModel())
                }

            }
        }
    }



}

