package mchou.apps.chat.db

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var uid: String? = "",
    var name: String? = "",
    var email: String? = "",
    var online: Boolean = false
):FbEntity{
    override fun setID(id: String) {
        uid = id
    }
}