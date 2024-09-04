package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}
class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private var playerTurn = true // true for X, false for O
    private var roundCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = Array(3) { r ->
            Array(3) { c ->
                findViewById<GridLayout>(R.id.gridLayout).getChildAt(r * 3 + c) as Button
            }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            resetGame()
        }

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].setOnClickListener {
                    onClick(it)
                }
            }
        }
    }

    private fun onClick(view: View) {
        if ((view as Button).text != "") {
            return
        }

        if (playerTurn) {
            view.text = "X"
        } else {
            view.text = "O"
        }

        roundCount++

        if (checkForWin()) {
            if (playerTurn) {
                playerWins("X")
            } else {
                playerWins("O")
            }
        } else if (roundCount == 9) {
            draw()
        } else {
            playerTurn = !playerTurn
            updateStatus()
        }
    }

    private fun checkForWin(): Boolean {
        val field = Array(3) { r ->
            Array(3) { c ->
                buttons[r][c].text.toString()
            }
        }

        // Check rows, columns, and diagonals
        for (i in 0..2) {
            if (field[i][0] == field[i][1] && field[i][1] == field[i][2] && field[i][0] != "") {
                return true
            }
        }

        for (i in 0..2) {
            if (field[0][i] == field[1][i] && field[1][i] == field[2][i] && field[0][i] != "") {
                return true
            }
        }

        if (field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[0][0] != "") {
            return true
        }

        if (field[0][2] == field[1][1] && field[1][1] == field[2][0] && field[0][2] != "") {
            return true
        }

        return false
    }

    private fun playerWins(winner: String) {
        findViewById<TextView>(R.id.statusTxt).text = "$winner Wins!"
        disableButtons()
    }

    private fun draw() {
        findViewById<TextView>(R.id.statusTxt).text = "Draw!"
    }

    private fun updateStatus() {
        val statusText = if (playerTurn) "Player X's Turn" else "Player O's Turn"
        findViewById<TextView>(R.id.statusTxt).text = statusText
    }

    private fun disableButtons() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    private fun resetGame() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
                buttons[i][j].isEnabled = true
            }
        }
        roundCount = 0
        playerTurn = true
        updateStatus()
    }
}