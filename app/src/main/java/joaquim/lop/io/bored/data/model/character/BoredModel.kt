package joaquim.lop.io.bored.data.model.character

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BoredModel(
    @SerializedName("activity") var activity: String,
    @SerializedName("type") var type: String,
    @SerializedName("participants") var participants: Int,
    @SerializedName("price") var price: Double,
    @SerializedName("link") var link: String,
    @SerializedName("key") var key: String,
    @SerializedName("accessibility") var accessibility: Double
) : Serializable