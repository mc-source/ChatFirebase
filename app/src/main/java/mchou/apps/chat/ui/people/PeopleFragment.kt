package mchou.apps.chat.ui.people

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_people.*
import kotlinx.android.synthetic.main.fragment_rooms.*
import mchou.apps.chat.R
import mchou.apps.chat.db.User


class PeopleFragment : Fragment() {
  private val TAG = "tests"
  private lateinit var homeViewModel: PeopleViewModel

  override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle? ): View? {
    homeViewModel = ViewModelProviders.of(this).get(PeopleViewModel::class.java)

    val root = inflater.inflate(R.layout.fragment_people, container, false)

    root.findViewById<TextView>(R.id.text_people_online).text=getString(R.string.title_people_online)
    root.findViewById<TextView>(R.id.text_people).text=getString(R.string.title_people)

    homeViewModel.users.observe(viewLifecycleOwner, Observer {
       var data = it //list users on change!
       Log.i(TAG, "onCreateView data : $data")

       updateList(data as List<User>)
    })
    return root
  }

  private fun updateList(data: List<User>?) {
      var adapter = FbRecyclerViewAdapter(context, data)
      list_people.layoutManager = LinearLayoutManager(context)
      list_people.adapter = adapter

      var onlines : MutableList<User> = mutableListOf()
      data!!.forEach { it ->
        if((it as User).online)
          onlines.add(it)
      }
      var adapter2 = FbRecyclerViewAdapter(context, onlines)
      list_online.layoutManager = LinearLayoutManager(context)
      list_online.adapter = adapter2

      list_people.layoutAnimation = AnimationUtils.loadLayoutAnimation(
          context, R.anim.layout_fall_down_animation
      )
      list_online.layoutAnimation = AnimationUtils.loadLayoutAnimation(
          context, R.anim.layout_fall_down_animation
      )
  }
}

class FbRecyclerViewAdapter(var context: Context?, var data: List<User>?) :
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
    val user: User? = data?.get(position)
    holder.name?.text = "${user?.name} [${user?.uid}]"
    holder.desc?.text = user?.email
  }

}
