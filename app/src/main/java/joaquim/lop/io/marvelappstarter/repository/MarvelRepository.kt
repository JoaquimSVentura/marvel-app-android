package joaquim.lop.io.marvelappstarter.repository

import joaquim.lop.io.marvelappstarter.data.remote.ServiceApi
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api: ServiceApi
) {
    suspend fun list() = api.list()
}