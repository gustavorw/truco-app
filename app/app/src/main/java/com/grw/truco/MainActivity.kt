package com.grw.truco

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grw.truco.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var scorePlayerOne = 0
    private var scorePlayerTwo = 0

    private var playerOneName = ""
    private var playerTwoName = ""

    private val MATCH_POINT = 12

    val matches = mutableListOf<Match>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnEditNicknames.setOnClickListener {
            openEditNickNames()
        }
        binding.btnViewMatches.setOnClickListener { openMatches() }
        binding.btnCleanScore.setOnClickListener { cleanScore() }

        playerOneName = binding.playerNameOne.text.toString()
        playerTwoName = binding.playerNameTwo.text.toString()

        binding.btnPlus1.setOnClickListener { updateScore(Player.One, 1) }
        binding.btnPlus3.setOnClickListener { updateScore(Player.One, 3) }
        binding.btnPlus6.setOnClickListener { updateScore(Player.One, 6) }
        binding.btnPlus9.setOnClickListener { updateScore(Player.One, 9) }
        binding.btnPlus12.setOnClickListener { updateScore(Player.One, 12) }

        binding.btnPlus1Player2.setOnClickListener { updateScore(Player.Two, 1) }
        binding.btnPlus3Player2.setOnClickListener { updateScore(Player.Two, 3) }
        binding.btnPlus6Player2.setOnClickListener { updateScore(Player.Two, 6) }
        binding.btnPlus9Player2.setOnClickListener { updateScore(Player.Two, 9) }
        binding.btnPlus12Player2.setOnClickListener { updateScore(Player.Two, 12) }
    }

    private fun itWon() {
        if (scorePlayerOne >= MATCH_POINT) {
            showToast(playerOneName)
            matches.add(Match(playerOneName, scorePlayerOne, playerTwoName, scorePlayerTwo))
            resetScore()
        }
        if (scorePlayerTwo >= MATCH_POINT) {
            showToast(playerTwoName)
            matches.add(Match(playerOneName, scorePlayerOne, playerTwoName, scorePlayerTwo))
            resetScore()
        }
    }

    private fun showToast(player: String) {
        Toast.makeText(this, "$player ganhou o jogo!", Toast.LENGTH_SHORT).show()
    }

private fun resetScore() {
    scorePlayerOne  = 0
    scorePlayerTwo = 0
    binding.scorePlayerOne.text = scorePlayerOne.toString()
    binding.scorePlayerTwo.text = scorePlayerTwo.toString()

    binding.playerNameOne.text = playerOneName
    binding.playerNameTwo.text = playerTwoName
}

    private fun updateScore(player: Player, points: Int) {
        when (player) {
            Player.One -> {
                scorePlayerOne += points
                binding.scorePlayerOne.text = scorePlayerOne.toString()

            }
            Player.Two -> {
                scorePlayerTwo += points
                binding.scorePlayerTwo.text = scorePlayerTwo.toString()
            }
        }
        itWon()
    }

    private val editNicknamesLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val nameOne = result.data?.getStringExtra("playerOneName")
            val nameTwo = result.data?.getStringExtra("playerTwoName")

            if (!nameOne.isNullOrEmpty()) {
                playerOneName = nameOne
                binding.playerNameOne.text = playerOneName
            }
            if (!nameTwo.isNullOrEmpty()) {
                playerTwoName = nameTwo
                binding.playerNameTwo.text = playerTwoName
            }
        }
    }

    private fun openEditNickNames() {
        val intent = Intent(this, EditNickNamesActivity::class.java)
        editNicknamesLauncher.launch(intent)
    }

    private fun openMatches() {
        val intent = Intent(this, MatchesView::class.java).apply {
            putExtra("matches", ArrayList(matches))
        }
        startActivity(intent)
    }

    private fun cleanScore(){
        matches.clear()
        resetScore()
    }
}