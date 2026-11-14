package com.grw.truco

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grw.truco.databinding.ActivityEditNicknamesBinding

class EditNickNamesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNicknamesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditNicknamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()


    }

    private fun setupListeners() {
        binding.btnConfirmNicknames.setOnClickListener {

            val playerOneName = binding.etPlayerOne.text.toString().trim()
            val playerTwoName = binding.etPlayerTwo.text.toString().trim()

            val resultIntent = Intent().apply {
                putExtra("playerOneName", playerOneName)
                putExtra("playerTwoName", playerTwoName)
            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}