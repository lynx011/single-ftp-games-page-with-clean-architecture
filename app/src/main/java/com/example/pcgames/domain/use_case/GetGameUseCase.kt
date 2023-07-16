package com.example.pcgames.domain.use_case

import com.example.pcgames.domain.model.GameList
import com.example.pcgames.domain.repository.GameRepository
import com.example.pcgames.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetGameUseCase @Inject constructor(private val repository: GameRepository) {
    suspend operator fun invoke(platform : String) : Flow<Response<List<GameList>>> = flow {
        try {
            emit(Response.Loading())
            val games = repository.getGamesList(platform = platform).map {
                it.toGamesList()
            }
            emit(Response.Success(games))
        }catch (e: HttpException){
            emit(Response.Error(e.localizedMessage?:"An expected Error Occurred"))
        }catch (e: IOException){
            emit(Response.Error("An Error Occurred"))
        }
    }
}