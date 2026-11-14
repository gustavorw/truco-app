package com.grw.truco

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.grw.truco.databinding.ActivityMatchesViewBinding

class MatchesView : AppCompatActivity() {
    private lateinit var binding: ActivityMatchesViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMatchesViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        @Suppress("UNCHECKED_CAST")
        val matchesList = intent.getSerializableExtra("matches") as? ArrayList<Match> ?: arrayListOf()

        if (matchesList.isEmpty()) {
            binding.tvEmptyState.visibility = View.VISIBLE
            binding.rvMatches.visibility = View.GONE
        } else {
            binding.tvEmptyState.visibility = View.GONE
            binding.rvMatches.visibility = View.VISIBLE
            binding.rvMatches.layoutManager = LinearLayoutManager(this)
            binding.rvMatches.adapter = MatchAdapter(matchesList)
        }
    }
}