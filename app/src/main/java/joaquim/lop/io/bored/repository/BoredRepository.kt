package joaquim.lop.io.bored.repository

import joaquim.lop.io.bored.data.remote.ServiceApi
import javax.inject.Inject

class BoredRepository @Inject constructor(
    private val api: ServiceApi
) {
    suspend fun list() = api.list()
}