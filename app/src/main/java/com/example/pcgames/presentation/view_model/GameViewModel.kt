package com.example.pcgames.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcgames.domain.use_case.GetGameUseCase
import com.example.pcgames.presentation.states.GameState
import com.example.pcgames.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val gameUseCase: GetGameUseCase) : ViewModel() {

    private val _gameList = MutableStateFlow(GameState())
    val gameList: StateFlow<GameState> = _gameList

    fun getGameList() = viewModelScope.launch(Dispatchers.IO) {
        gameUseCase(platform = "pc").collect {
            when (it) {
                is Response.Loading -> {
                    _gameList.value = gameList.value.copy(
                        loading = true,
                        gameList = it.data ?: emptyList()
                    )
                }

                is Response.Success -> {
                    _gameList.value = gameList.value.copy(
                        loading = false,
                        gameList = it.data ?: emptyList()
                    )
                }

                is Response.Error -> {
                    _gameList.value = gameList.value.copy(
                        loading = false,
                        error = it.message ?: "An Expected Error Occurred!"
                    )
                }
            }
        }
    }

    fun onClear() {
        _gameList.value = GameState(false, emptyList(), "")
    }
}