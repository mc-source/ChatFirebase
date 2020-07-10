package mchou.apps.chat.db

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Room(
    var roomid: String? = "",
    var name: String? = "",
    var author: String? = "",
    var created:   Long = 0, //TimeStamp
    var active: Boolean = true
    //var stars: MutableMap<String, Boolean> = HashMap()
):FbEntity{
    override fun setID(id: String) {
        roomid = id
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            //"roomid" to roomid,
            "author" to author,
            "name" to name,
            "created" to created,
            "active" to active
        )
    }
}