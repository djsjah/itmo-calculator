package com.voyager177

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.voyager177.core.logic.CalculatorEngine
import com.voyager177.core.logic.DefaultCalculatorEngine
import com.voyager177.core.ui.CalculatorScreen
import com.voyager177.ui.theme.Voyager177Theme

class MainActivity : ComponentActivity() {
    private val calculatorEngine: CalculatorEngine = DefaultCalculatorEngine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Voyager177Theme {
                CalculatorScreen(
                    calculatorEngine = calculatorEngine
                )
            }
        }
    }
}