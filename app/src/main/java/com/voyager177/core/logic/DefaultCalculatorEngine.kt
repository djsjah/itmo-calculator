package com.voyager177.core.logic

class DefaultCalculatorEngine : CalculatorEngine {
    override fun calculate(
        firstNumber: Double,
        secondNumber: Double,
        operation: Operation
    ): Double? {
        return when (operation) {
            Operation.ADD -> firstNumber + secondNumber

            Operation.SUBTRACT -> firstNumber - secondNumber

            Operation.MULTIPLY -> firstNumber * secondNumber

            Operation.DIVIDE -> {
                if (secondNumber == 0.0) null
                else firstNumber / secondNumber
            }
        }
    }
}