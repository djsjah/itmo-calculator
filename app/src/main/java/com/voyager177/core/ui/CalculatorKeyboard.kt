package com.voyager177.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voyager177.core.logic.Operation

private val NumberButtonColor = Color(0xFF333333)
private val OperationButtonColor = Color(0xFFFF9F0A)
private val ClearButtonColor = Color(0xFFA5A5A5)

@Composable
internal fun CalculatorKeyboard(
    selectedOperation: Operation?,
    onDigitClick: (Char) -> Unit,
    onDecimalClick: () -> Unit,
    onOperationClick: (Operation) -> Unit,
    onEqualsClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        CalculatorRow(modifier = Modifier.weight(1f)) {
            CalculatorButton(
                text = "C",
                backgroundColor = ClearButtonColor,
                textColor = Color.Black,
                onClick = onClearClick
            )

            Spacer(modifier = Modifier.weight(2f))

            OperationButton(
                operation = Operation.DIVIDE,
                selectedOperation = selectedOperation,
                onOperationClick = onOperationClick
            )
        }

        CalculatorRow(modifier = Modifier.weight(1f)) {
            NumberButton('7', onDigitClick)
            NumberButton('8', onDigitClick)
            NumberButton('9', onDigitClick)

            OperationButton(
                operation = Operation.MULTIPLY,
                selectedOperation = selectedOperation,
                onOperationClick = onOperationClick
            )
        }

        CalculatorRow(modifier = Modifier.weight(1f)) {
            NumberButton('4', onDigitClick)
            NumberButton('5', onDigitClick)
            NumberButton('6', onDigitClick)

            OperationButton(
                operation = Operation.SUBTRACT,
                selectedOperation = selectedOperation,
                onOperationClick = onOperationClick
            )
        }

        CalculatorRow(modifier = Modifier.weight(1f)) {
            NumberButton('1', onDigitClick)
            NumberButton('2', onDigitClick)
            NumberButton('3', onDigitClick)

            OperationButton(
                operation = Operation.ADD,
                selectedOperation = selectedOperation,
                onOperationClick = onOperationClick
            )
        }

        CalculatorRow(modifier = Modifier.weight(1f)) {
            CalculatorButton(
                text = "0",
                backgroundColor = NumberButtonColor,
                onClick = {
                    onDigitClick('0')
                },
                weight = 2f,
                shape = RoundedCornerShape(50)
            )

            CalculatorButton(
                text = ".",
                backgroundColor = NumberButtonColor,
                onClick = onDecimalClick
            )

            CalculatorButton(
                text = "=",
                backgroundColor = OperationButtonColor,
                onClick = onEqualsClick
            )
        }
    }
}

@Composable
private fun CalculatorRow(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = content
    )
}

@Composable
private fun RowScope.NumberButton(number: Char, onDigitClick: (Char) -> Unit) {
    CalculatorButton(
        text = number.toString(),
        backgroundColor = NumberButtonColor,
        onClick = {
            onDigitClick(number)
        }
    )
}

@Composable
private fun RowScope.OperationButton(
    operation: Operation,
    selectedOperation: Operation?,
    onOperationClick: (Operation) -> Unit
) {
    val selected = operation == selectedOperation

    CalculatorButton(
        text = operation.symbol.toString(),
        backgroundColor = if (selected) {
            Color.White
        } else {
            OperationButtonColor
        },

        textColor = if (selected) {
            OperationButtonColor
        } else {
            Color.White
        },

        onClick = {
            onOperationClick(operation)
        }
    )
}

@Composable
private fun RowScope.CalculatorButton(
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    weight: Float = 1f,
    textColor: Color = Color.White,
    shape: Shape = CircleShape
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(weight)
            .fillMaxHeight(),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        )
    ) {
        Text(
            text = text,
            fontSize = 28.sp
        )
    }
}