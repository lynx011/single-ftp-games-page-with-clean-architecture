package com.example.pcgames.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcgames.domain.model.GameList
import com.example.pcgames.domain.use_case.GetGameUseCase
import com.example.pcgames.presentation.states.GameState
import com.example.pcgames.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val gameUseCase: GetGameUseCase) : ViewModel() {

    private val _gameList = MutableStateFlow(GameState())
    val gameList : StateFlow<GameState> = _gameList

    fun getGameList() = viewModelScope.launch(Dispatchers.IO) {
        gameUseCase(platform = "pc").collect{
            when(it){
                is Response.Loading -> {
                   _gameList.value = GameState(loading = true)
                }
                is Response.Success -> {
                    _gameList.value = GameState(gameList = it.data?: emptyList())
                }
                is Response.Error -> {
                    _gameList.value = GameState(error = it.message?:"An Unexpected Error Occurred!")
                }
            }
        }
    }

    fun onClear(){
        _gameList.value = GameState(false, emptyList(),"")
    }
}