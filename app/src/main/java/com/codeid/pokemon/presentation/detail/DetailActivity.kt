package com.codeid.pokemon.presentation.detail


import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.codeid.pokemon.R
import com.codeid.pokemon.domain.model.Pokemon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val pokemonName = intent.getStringExtra("pokemon_name") ?: return

        val txtName = findViewById<TextView>(R.id.txtDetailName)
        val txtAbilities = findViewById<TextView>(R.id.txtAbilities)

        lifecycleScope.launch {
            val detail: Pokemon = viewModel.getDetail(pokemonName)
            txtName.text = detail.name.capitalize()
            txtAbilities.text = detail.abilities.joinToString("\n") { it.name }
        }
    }
}