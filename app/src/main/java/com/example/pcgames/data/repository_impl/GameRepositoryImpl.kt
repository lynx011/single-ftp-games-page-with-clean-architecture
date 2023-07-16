package com.example.pcgames.data.repository_impl

import com.example.pcgames.data.data_source.api_service.GameApiService
import com.example.pcgames.domain.model.GameList
import com.example.pcgames.domain.repository.GameRepository
import com.example.pcgames.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val apiService: GameApiService
) :
    GameRepository {
    override suspend fun getGamesList(platform: String): Flow<Response<List<GameList>>> = flow {
        try {
            emit(Response.Loading())
            val gameList = apiService.getGames(platform = platform).map {
                it.toGamesList()
            }
            emit(Response.Success(data = gameList))
        } catch (e: HttpException) {
            emit(Response.Error(e.localizedMessage ?: "HttpException-An Expected Error Occurred!"))
        } catch (e: IOException) {
            emit(Response.Error(e.localizedMessage ?: "IOException-An Expected Error Occurred!"))
        }
    }
}