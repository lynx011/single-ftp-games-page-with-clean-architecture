package com.example.pcgames.presentation.states

import com.example.pcgames.domain.model.GameList

data class GameState(
    val loading: Boolean = false,
    val gameList: List<GameList> = emptyList(),
    val error: String = ""
)
