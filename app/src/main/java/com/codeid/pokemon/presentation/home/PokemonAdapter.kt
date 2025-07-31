package com.codeid.pokemon.presentation.home


import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codeid.pokemon.R
import com.codeid.pokemon.domain.model.Pokemon
import com.codeid.pokemon.presentation.detail.DetailActivity

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private val items = mutableListOf<Pokemon>()

    fun submitList(newList: List<Pokemon>) {
        val start = items.size
        items.addAll(newList)
        notifyItemRangeInserted(start, newList.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtName: TextView = itemView.findViewById(R.id.txtName)

        fun bind(pokemon: Pokemon) {
            txtName.text = pokemon.name.capitalize()
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("pokemon_name", pokemon.name)
                }
                context.startActivity(intent)
            }
        }
    }
}
