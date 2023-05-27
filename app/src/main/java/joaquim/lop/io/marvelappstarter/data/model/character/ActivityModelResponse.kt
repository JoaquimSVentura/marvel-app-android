package joaquim.lop.io.marvelappstarter.data.model.character

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ActivityModelResponse(
    @SerializedName("data")
    val data: ActivityModelData
) : Serializable
