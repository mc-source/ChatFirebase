package mchou.apps.chat

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import mchou.apps.chat.db.Dao

class MainActivity : AppCompatActivity() {
    private val TAG = "tests"
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(getColor(R.color.primaryColor)))

        //toolbar.setLogo(R.drawable.chat_logo)


        val fab: FloatingActionButton = findViewById(R.id.fab)
        //fab.visibility = View.INVISIBLE
        fab.setOnClickListener { view ->
            val action : (View?) -> Unit = { _: View? -> openDialog()}

            Snackbar.make(view, "add..", Snackbar.LENGTH_LONG)
                .setAction("Add", action)
                .show()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery //, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        AuthInfo(navView)
        DatabaseInfo()
    }
    private fun openDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle("Add Item..")
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher)
        alertDialogBuilder.setCancelable(false)

        val layoutInflater = LayoutInflater.from(this)
        val dialogView = layoutInflater.inflate(R.layout.input_layout, null)

        alertDialogBuilder.setView(dialogView)

        val dialog = alertDialogBuilder.create()
        dialog.show()

        dialogView.findViewById<Button>(R.id.btn_cancel_item).setOnClickListener {
            dialog!!.cancel()
            //setList()
        }
        dialogView.findViewById<Button>(R.id.btn_save_item).setOnClickListener {

            val name = dialogView.findViewById<EditText>(R.id.inputName).text.toString()
            val password = dialogView.findViewById<EditText>(R.id.inputPassword).text.toString()
            val login = dialogView.findViewById<EditText>(R.id.inputLogin).text.toString()

            if(name.isNotEmpty() && password.isNotEmpty() && login.isNotEmpty()) {
                /*val dao = Dao(SafeActivity@ this)
                var ok = dao.create(name, login, AESCrypt.encrypt(password), "")
                if (ok) {
                    toast("Item inserted! :)")
                    adapter.refresh()
                    adapter.notifyItemInserted(adapter.itemCount)
                    setList()
                }*/
            }
            //adapter.notifyDataSetChanged()
            dialog.cancel()
        }
    }
    private fun DatabaseInfo() {
        val dao = Dao()
        Log.i(TAG, "Dao : $dao")
    }

    private fun AuthInfo(navView: NavigationView) {
        val user = FirebaseAuth.getInstance().currentUser
        //val user = mAuth!!.currentUser

        user?.let{
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl: Uri? = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = user.uid


/*            var result = "Current User :\r\n $name | $email : $emailVerified"
            findViewById<TextView>(R.id.result).text = result*/

            navView.getHeaderView(0).findViewById<TextView>(R.id.header_user_name).text=name
            navView.getHeaderView(0).findViewById<TextView>(R.id.header_user_email).text=email
        }
    }

/*    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}