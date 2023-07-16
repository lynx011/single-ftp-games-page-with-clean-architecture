package com.example.pcgames.data.repository_impl

import com.example.pcgames.data.data_source.api_service.GameApiService
import com.example.pcgames.data.data_source.game_list_dto.GameListDTO
import com.example.pcgames.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val apiService: GameApiService) : GameRepository{
    override suspend fun getGamesList(platform: String): GameListDTO {
        return  apiService.getGames(platform = platform)
    }
}