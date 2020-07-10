package mchou.apps.chat.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import mchou.apps.chat.R

class ChatFragment : Fragment() {

  private lateinit var dashboardViewModel: ChatViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    dashboardViewModel =
    ViewModelProviders.of(this).get(ChatViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_chat, container, false)
    val textView: TextView = root.findViewById(R.id.text_chat)
    dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })
    return root
  }
}