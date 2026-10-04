package com.voyager177.core.logic

interface CalculatorEngine {
    fun calculate(
        firstNumber: Double,
        secondNumber: Double,
        operation: Operation
    ): Double?
}