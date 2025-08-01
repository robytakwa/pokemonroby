package com.codeid.pokemon.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codeid.pokemon.databinding.FragmentHomeBinding
import com.codeid.pokemon.domain.model.Pokemon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = HomeAdapter()
    private var originalPokemonList = mutableListOf<Pokemon>()
    private var isLoading = false
    private var offset = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            val pokemons = viewModel.getPokemons()
            originalPokemonList.addAll(pokemons)
            adapter.submitList(originalPokemonList.toList())
            offset += pokemons.size
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!isLoading && lastVisibleItem == totalItemCount - 1) {
                    isLoading = true
                    lifecycleScope.launch {
                        val newPokemons = viewModel.getPokemons(offset)
                        originalPokemonList.addAll(newPokemons)
                        adapter.submitList(originalPokemonList.toList())
                        offset += newPokemons.size
                        isLoading = false
                    }
                }
            }
        })

        binding.searchInput.addTextChangedListener {
            val query = it.toString()
            val filtered = viewModel.searchPokemonByName(originalPokemonList, query)
            adapter.submitList(filtered)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
