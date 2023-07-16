package com.example.pcgames.domain.use_case

import com.example.pcgames.domain.model.GameList
import com.example.pcgames.domain.repository.GameRepository
import com.example.pcgames.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGameUseCase @Inject constructor(private val repository: GameRepository) {
    suspend operator fun invoke(platform: String): Flow<Response<List<GameList>>> {
        return if (platform.isEmpty()) {
            flow { }
        } else {
            repository.getGamesList(platform = platform)
        }
    }
}