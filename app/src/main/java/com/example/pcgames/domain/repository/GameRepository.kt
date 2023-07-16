package com.example.pcgames.domain.repository

import com.example.pcgames.data.data_source.game_list_dto.GameListDTO

interface GameRepository {

    suspend fun getGamesList(platform: String) : GameListDTO
}