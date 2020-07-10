package mchou.apps.chat.ui.rooms

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mchou.apps.chat.db.Dao
import mchou.apps.chat.db.User

class RoomsViewModel : ViewModel() {

   /* private val _text = MutableLiveData<String>().apply {
        value = "Rooms Fragment"
    }
    val text: LiveData<String> = _text*/

    var rooms = Dao().getItems("rooms")
    lateinit var user : MutableLiveData<User>
    init {
        //Log.i(TAG , "rooms: $rooms")
    }
}