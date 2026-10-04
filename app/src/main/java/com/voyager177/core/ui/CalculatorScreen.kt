package com.voyager177.core.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voyager177.R
import com.voyager177.core.logic.CalculatorEngine
import com.voyager177.core.logic.Operation

private const val MAX_INPUT_LENGTH = 15

private val CalculatorBackgroundColor = Color.Black

@Composable
fun CalculatorScreen(calculatorEngine: CalculatorEngine) {
    var currentInput by rememberSaveable {
        mutableStateOf("0")
    }

    var firstNumber by rememberSaveable {
        mutableDoubleStateOf(0.0)
    }

    var operation by rememberSaveable {
        mutableStateOf<Operation?>(null)
    }

    var startNewNumber by rememberSaveable {
        mutableStateOf(true)
    }

    var hasError by rememberSaveable {
        mutableStateOf(false)
    }

    fun clear() {
        currentInput = "0"
        firstNumber = 0.0
        operation = null
        startNewNumber = true
        hasError = false
    }

    fun showError() {
        currentInput = "0"
        firstNumber = 0.0
        operation = null
        startNewNumber = true
        hasError = true
    }

    fun inputDigit(digit: Char) {
        if (hasError) clear()

        if (startNewNumber) {
            currentInput = digit.toString()
            startNewNumber = false
            return
        }

        if (currentInput == "0") {
            currentInput = digit.toString()
            return
        }

        if (currentInput.length < MAX_INPUT_LENGTH) currentInput += digit
    }

    fun inputDecimalPoint() {
        if (hasError) clear()

        if (startNewNumber) {
            currentInput = "0."
            startNewNumber = false
            return
        }

        if (
            !currentInput.contains('.') &&
            currentInput.length < MAX_INPUT_LENGTH
        ) {
            currentInput += '.'
        }
    }

    fun selectOperation(newOperation: Operation) {
        if (hasError) return

        val currentNumber = currentInput.toDoubleOrNull() ?: return
        val currentOperation = operation

        if (
            currentOperation != null &&
            !startNewNumber
        ) {
            val result = calculatorEngine.calculate(
                firstNumber = firstNumber,
                secondNumber = currentNumber,
                operation = currentOperation
            )

            if (result == null || !result.isFinite()) {
                showError()
                return
            }

            firstNumber = result
            currentInput = formatNumber(result)
        }
        else if (currentOperation == null) {
            firstNumber = currentNumber
        }

        operation = newOperation
        startNewNumber = true
    }

    fun calculateResult() {
        val currentOperation = operation ?: return
        if (hasError || startNewNumber) return

        val secondNumber = currentInput.toDoubleOrNull() ?: return

        val result = calculatorEngine.calculate(
            firstNumber = firstNumber,
            secondNumber = secondNumber,
            operation = currentOperation
        )

        if (result == null || !result.isFinite()) {
            showError()
            return
        }

        currentInput = formatNumber(result)
        firstNumber = result
        operation = null
        startNewNumber = true
    }

    val currentOperation = operation

    val displayText = when {
        hasError -> {
            stringResource(
                R.string.calculation_error
            )
        }

        currentOperation != null && startNewNumber -> {
            buildString {
                append(formatNumber(firstNumber))
                append(' ')
                append(currentOperation.symbol)
            }
        }

        currentOperation != null -> {
            buildString {
                append(formatNumber(firstNumber))
                append(' ')
                append(currentOperation.symbol)
                append(' ')
                append(currentInput)
            }
        }

        else -> currentInput
    }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeCalculator(
            display = displayText,
            selectedOperation = operation,
            onDigitClick = ::inputDigit,
            onDecimalClick = ::inputDecimalPoint,
            onOperationClick = ::selectOperation,
            onEqualsClick = ::calculateResult,
            onClearClick = ::clear
        )
    }
    else {
        PortraitCalculator(
            display = displayText,
            selectedOperation = operation,
            onDigitClick = ::inputDigit,
            onDecimalClick = ::inputDecimalPoint,
            onOperationClick = ::selectOperation,
            onEqualsClick = ::calculateResult,
            onClearClick = ::clear
        )
    }
}

@Composable
private fun PortraitCalculator(
    display: String,
    selectedOperation: Operation?,
    onDigitClick: (Char) -> Unit,
    onDecimalClick: () -> Unit,
    onOperationClick: (Operation) -> Unit,
    onEqualsClick: () -> Unit,
    onClearClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CalculatorBackgroundColor)
            .safeDrawingPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        CalculatorDisplay(
            display = display,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f),
            compact = false
        )

        CalculatorKeyboard(
            selectedOperation = selectedOperation,
            onDigitClick = onDigitClick,
            onDecimalClick = onDecimalClick,
            onOperationClick = onOperationClick,
            onEqualsClick = onEqualsClick,
            onClearClick = onClearClick,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.4f)
        )
    }
}

@Composable
private fun LandscapeCalculator(
    display: String,
    selectedOperation: Operation?,
    onDigitClick: (Char) -> Unit,
    onDecimalClick: () -> Unit,
    onOperationClick: (Operation) -> Unit,
    onEqualsClick: () -> Unit,
    onClearClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(CalculatorBackgroundColor)
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        CalculatorDisplay(
            display = display,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            compact = true
        )

        Spacer(modifier = Modifier.width(16.dp))

        CalculatorKeyboard(
            selectedOperation = selectedOperation,
            onDigitClick = onDigitClick,
            onDecimalClick = onDecimalClick,
            onOperationClick = onOperationClick,
            onEqualsClick = onEqualsClick,
            onClearClick = onClearClick,
            modifier = Modifier
                .weight(1.4f)
                .fillMaxHeight()
        )
    }
}

@Composable
private fun CalculatorDisplay(display: String, modifier: Modifier = Modifier, compact: Boolean) {
    val scrollState = rememberScrollState()

    LaunchedEffect(display, scrollState.maxValue) {
        scrollState.scrollTo(
            scrollState.maxValue
        )
    }

    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        SelectionContainer {
            Text(
                text = display,
                modifier = Modifier
                    .horizontalScroll(scrollState),
                color = Color.White,
                fontSize = if (compact) {
                    42.sp
                } else {
                    56.sp
                },
                textAlign = TextAlign.End,
                maxLines = 1,
                softWrap = false
            )
        }
    }
}