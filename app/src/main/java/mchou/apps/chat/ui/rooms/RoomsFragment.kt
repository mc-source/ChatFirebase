package mchou.apps.chat.ui.rooms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_rooms.*
import mchou.apps.chat.R
import mchou.apps.chat.db.Dao
import mchou.apps.chat.db.Room
import mchou.apps.chat.db.User
import java.text.SimpleDateFormat
import java.util.*

class RoomsFragment : Fragment() {
  private val TAG = "tests"
  private lateinit var notificationsViewModel: RoomsViewModel

  override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle? ): View? {
    notificationsViewModel = ViewModelProviders.of(this).get(RoomsViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_rooms, container, false)

    root.findViewById<TextView>(R.id.text_rooms).text = getString(R.string.title_rooms)
    notificationsViewModel.rooms.observe(viewLifecycleOwner, Observer {
        var data = it
        Log.i(TAG, "onCreateView data : $data")
        updateList(data as List<Room>)
    })

    return root
  }

  private fun updateList(data: List<Room>?) {
      val adapter = FbRecyclerViewAdapter(context, data, viewLifecycleOwner)
      list_rooms.layoutManager = LinearLayoutManager(context)
      list_rooms.adapter = adapter

      list_rooms.layoutAnimation = AnimationUtils.loadLayoutAnimation(
          context, R.anim.layout_fall_down_animation
      )
  }
}
class FbRecyclerViewAdapter(var context: Context?, var data: List<Room>?, var viewLifecycleOwner : LifecycleOwner) :
  RecyclerView.Adapter<FbRecyclerViewAdapter.FbViewHolder>() {

  override fun getItemCount(): Int {
    return data?.size?:0
  }

  class FbViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var pic: ImageView? = null
    var name: TextView? = null
    var desc: TextView? = null

    init {
      pic = itemView.findViewById<ImageView>(R.id.item_pic)
      name = itemView.findViewById<TextView>(R.id.item_name)
      desc = itemView.findViewById<TextView>(R.id.item_desc)
    }

  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FbViewHolder {
    val rootView: View =  LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)

    return FbViewHolder(rootView)
  }
  override fun onBindViewHolder(holder: FbViewHolder, position: Int) {
      val room: Room? = data?.get(position)

      val netDate = room?.created?.let { Date(it) }
      val date = SimpleDateFormat("dd/MM/yy hh:mm a").format(netDate)
      holder.name?.text = room?.name?.toUpperCase() //"${room?.name} [${room?.roomid}]"

      var author = Dao().getUser(room?.author!!)
      author.observe(viewLifecycleOwner, Observer {
          var author = it?.let { it.name }
          if (author == null)
              author="?"

          holder.desc?.text = "${author.toUpperCase()} [$date]"
      })

      //holder.pic?.setImageResource(R.drawable.chat_room)
  }

}