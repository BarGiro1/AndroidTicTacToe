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
    private var currentPlayer = "X"
    private val board = Array(3) { arrayOfNulls<String>(3) }

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
    }

    private fun onCellClick(index: Int) {
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

            // Switch player
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            tvGameStatus.text = "$currentPlayer Play"
            
        } else {
            Toast.makeText(this, "Cell already occupied!", Toast.LENGTH_SHORT).show()
        }
    }
}
