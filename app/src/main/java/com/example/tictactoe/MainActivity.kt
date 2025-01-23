package com.example.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var gridButtons: Array<Button>
    private lateinit var tvGameStatus: TextView
    private lateinit var btnPlayAgain: Button
    private var currentPlayer = "X"
    private val board = Array(3) { arrayOfNulls<String>(3) }
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvGameStatus = findViewById(R.id.tvGameStatus)
        btnPlayAgain = findViewById(R.id.btnPlayAgain)

        gridButtons = Array(9) { i ->
            findViewById(resources.getIdentifier("button$i", "id", packageName))
        }

        // Initialize button listeners
        for (i in gridButtons.indices) {
            gridButtons[i].setOnClickListener { onCellClick(i) }
        }

        btnPlayAgain.setOnClickListener { resetGame() }
    }

    private fun onCellClick(index: Int) {
        if (!gameActive) return

        val row = index / 3
        val col = index % 3

        // Check if cell is empty
        if (board[row][col] == null) {
            board[row][col] = currentPlayer
            gridButtons[index].text = currentPlayer
            gridButtons[index].setTextColor(
                Color.parseColor(
                if (currentPlayer == "X") "red"
                else "blue")
            )

            if (checkWin(row, col)) {
                tvGameStatus.text = "Player $currentPlayer Wins!"
                gameActive = false
                btnPlayAgain.visibility = View.VISIBLE
            } else if (board.flatten().all { it != null }) {
                tvGameStatus.text = "It's a Draw!"
                gameActive = false
                btnPlayAgain.visibility = View.VISIBLE
            } else {
                // Switch player
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                tvGameStatus.text = "$currentPlayer Play"
            }
        } else {
            Toast.makeText(this, "Cell already occupied!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkWin(row: Int, col: Int): Boolean {
        // Check row, column, and diagonals
        return (board[row].all { it == currentPlayer } ||
                board.map { it[col] }.all { it == currentPlayer } ||
                (row == col && board.indices.all { board[it][it] == currentPlayer }) ||
                (row + col == 2 && board.indices.all { board[it][2 - it] == currentPlayer }))
    }

    private fun resetGame() {
        board.forEachIndexed { i, row -> row.fill(null) }
        gridButtons.forEach { it.text = "" }
        currentPlayer = "X"
        gameActive = true
        tvGameStatus.text = "Player X's Turn"
        btnPlayAgain.visibility = View.GONE
    }
}
