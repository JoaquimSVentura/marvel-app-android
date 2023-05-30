package joaquim.lop.io.bored.data.remote

import joaquim.lop.io.bored.data.model.character.BoredModel
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {

    @GET("activity")
    suspend fun list(): Response<BoredModel>
}
