package mchou.apps.chat.db

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Msg(
    var msgid: String? = "",
    var body: String? = "",
    var author: String? = "",
    var roomid: String? = "",
    var created: Long = 0      //TimeStamp
    //var stars: MutableMap<String, Boolean> = HashMap()
):FbEntity{
    
    override fun setID(id: String) {
        msgid = id
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            //"msgid" to msgid,
            "author" to author,
            "roomid" to roomid,
            "body" to body,
            "created" to created
        )
    }
}