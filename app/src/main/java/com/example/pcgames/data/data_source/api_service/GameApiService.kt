package com.example.pcgames.data.data_source.api_service

import com.example.pcgames.data.data_source.game_list_dto.GameListDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApiService {
    @GET("api/games")
    suspend fun getGames(@Query("platform") platform: String): GameListDTO
}