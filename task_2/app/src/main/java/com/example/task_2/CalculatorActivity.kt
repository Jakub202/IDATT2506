package com.example.task_2

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.task_2.R
import kotlin.random.Random

class CalculatorActivity : Activity() {

    private lateinit var tvNumber1: TextView
    private lateinit var tvNumber2: TextView
    private lateinit var etAnswer: EditText
    private lateinit var etRange: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        tvNumber1 = findViewById(R.id.tvNumber1)
        tvNumber2 = findViewById(R.id.tvNumber2)
        etAnswer = findViewById(R.id.etAnswer)
        etRange = findViewById(R.id.etRange)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)

        btnAdd.setOnClickListener {
            checkAnswer(true)
        }

        btnMultiply.setOnClickListener {
            checkAnswer(false)
        }

        // Kall denne metoden for å initiere tallene første gang
        setRandomNumbers()
    }

    private fun checkAnswer(isAddition: Boolean) {
        val num1 = tvNumber1.text.toString().toInt()
        val num2 = tvNumber2.text.toString().toInt()
        val answer = etAnswer.text.toString().toInt()
        val correctAnswer = if (isAddition) num1 + num2 else num1 * num2

        if (answer == correctAnswer) {
            Toast.makeText(this, "Riktig", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Feil. Riktig svar er $correctAnswer", Toast.LENGTH_SHORT).show()
        }

        // Setter nye tilfeldige tall
        setRandomNumbers()
    }

    private fun setRandomNumbers() {
        // Forutsatt at du har en måte å sette øvre grense (her hardkodet til 100)
        val upperLimit = etRange.text.toString().toInt()
        tvNumber1.text = (Random.nextInt(upperLimit) + 1).toString()
        tvNumber2.text = (Random.nextInt(upperLimit) + 1).toString()
    }
}
