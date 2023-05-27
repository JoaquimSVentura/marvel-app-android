package joaquim.lop.io.marvelappstarter.data.model.character

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ActivityModelData(
    @SerializedName("results")
    val results: ActivityModel
) : Serializable
