package com.example.pcgames.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pcgames.databinding.GameItemBinding
import com.example.pcgames.domain.model.GameList

class GameAdapter : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(private val binding: GameItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(games: GameList){
            binding.games = games
            binding.executePendingBindings()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GameItemBinding.inflate(inflater,parent,false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameAdapter.GameViewHolder, position: Int) {
        val games = differ.currentList[position]
        games?.let(holder::bind)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<GameList>(){
        override fun areItemsTheSame(oldItem: GameList, newItem: GameList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GameList, newItem: GameList): Boolean {
            return oldItem.id == newItem.id
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)
}