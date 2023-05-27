package joaquim.lop.io.marvelappstarter.data.remote

import joaquim.lop.io.marvelappstarter.data.model.character.ActivityModelResponse
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {

    @GET("activity")
    suspend fun list(): Response<ActivityModelResponse>
}
