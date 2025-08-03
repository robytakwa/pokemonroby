package com.codeid.pokemon.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.codeid.pokemon.databinding.FragmentHomeBinding
import com.codeid.pokemon.presentation.common.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private val adapter = HomeAdapter()
    private var offset = 0
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelFactory.inject(requireContext()).create(HomeViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                binding.searchInput.addTextChangedListener {
                    val query = it.toString()
                    val filtered = viewModel.searchPokemon(query, adapter.currentList)
                    adapter.submitList(filtered)
                }


                if (!isLoading && lastVisibleItem >= totalItemCount - 1) {
                    isLoading = true
                    showLoading(true)
                    lifecycleScope.launch {
                        val newPokemons = viewModel.getPokemons(offset)
                        offset += newPokemons.size
                        adapter.submitList(adapter.currentList + newPokemons)
                        isLoading = false
                        showLoading(false)
                    }
                }
            }
        })

        lifecycleScope.launch {
            showLoading(true)
            delay(1000)
            val pokemons = viewModel.getPokemons(offset)
            offset += pokemons.size
            adapter.submitList(pokemons)
            showLoading(false)
        }
    }

    private fun showLoading(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
