package com.example.pcgames.domain.repository

import com.example.pcgames.domain.model.GameList
import com.example.pcgames.utils.Response
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun getGamesList(platform: String): Flow<Response<List<GameList>>>
}