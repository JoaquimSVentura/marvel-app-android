package joaquim.lop.io.marvelappstarter.repository

import joaquim.lop.io.marvelappstarter.data.local.MarvelDao
import joaquim.lop.io.marvelappstarter.data.model.character.CharacterModel
import joaquim.lop.io.marvelappstarter.data.remote.ServiceApi
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api: ServiceApi,
    private val dao: MarvelDao
) {
    suspend fun list(nameStartsWith: String? = null) = api.list(nameStartsWith)
    suspend fun getComics(characterId: Int) = api.getComics(characterId)

    suspend fun insert(characterModel: CharacterModel) = dao.insert(characterModel)
    fun getAll() = dao.getAll()
    suspend fun delete(characterModel: CharacterModel) = dao.delete(characterModel)
}