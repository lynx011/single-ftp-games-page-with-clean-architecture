package com.example.pcgames.data.data_source.game_list_dto
import com.example.pcgames.domain.model.GameList

class GameListDTO : ArrayList<GameListItem>()

data class GameListItem(
    val developer: String,
    val freetogame_profile_url: String,
    val game_url: String,
    val genre: String,
    val id: Int,
    val platform: String,
    val publisher: String,
    val release_date: String,
    val short_description: String,
    val thumbnail: String,
    val title: String
) {
    fun toGamesList(): GameList {
        return GameList(
            developer = developer,
            freetogame_profile_url = freetogame_profile_url,
            game_url = game_url,
            genre = genre,
            id = id,
            platform = platform,
            publisher = publisher,
            release_date = release_date,
            short_description = short_description,
            thumbnail = thumbnail,
            title = title
        )
    }
}