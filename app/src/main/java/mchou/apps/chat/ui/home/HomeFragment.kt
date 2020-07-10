package mchou.apps.chat.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import mchou.apps.chat.R

class HomeFragment : Fragment() {
    private val TAG = "tests"
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

       // val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
            Log.i(TAG , "onCreateView: $it")
            Log.i(TAG, "onCreateView: ${(activity as AppCompatActivity).supportActionBar?.title} ")
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation(view)
        //(activity as AppCompatActivity).supportActionBar?.subtitle = "item : ${bottomNavigationView.selectedItemId}"
    }
    private fun setupNavigation(view: View) {
        val title = (activity as AppCompatActivity).supportActionBar?.title;

        val bottomNavigationView: BottomNavigationView = view.findViewById(R.id.nav_view_chat)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_chat)

        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, _, _ ->
            (activity as AppCompatActivity).supportActionBar?.title  = "$title : ${navController.currentDestination?.label}"
        }
    }
}