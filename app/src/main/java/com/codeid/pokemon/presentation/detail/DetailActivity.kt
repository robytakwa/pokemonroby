package com.codeid.pokemon.presentation.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.codeid.pokemon.databinding.ActivityDetailBinding
import com.codeid.pokemon.presentation.common.ViewModelFactory
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonName = intent.getStringExtra("pokemon_name") ?: return

        viewModel = ViewModelFactory.inject(this).create(DetailViewModel::class.java)

        lifecycleScope.launch {
            try {
                val pokemon = viewModel.getDetail(pokemonName)
                binding.txtDetailName.text = pokemon.name

                binding.txtAbilities.text = "Abilities:\n" + pokemon.abilities.joinToString("\n") { it.name }

            } catch (e: Exception) {
                Toast.makeText(this@DetailActivity, "Failed to load detail", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
