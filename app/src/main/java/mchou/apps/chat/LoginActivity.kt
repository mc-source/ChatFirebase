package mchou.apps.chat

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse

class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN: Int = 1822

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()

        authChooser()
    }

    private fun authChooser() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.TwitterBuilder().build()
        )

        //btn_sign_in.setOnClickListener{
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.AuthenticationTheme)
                .setLogo(R.drawable.firebase_auth_120dp)
                .build(),
            RC_SIGN_IN
        )
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this@LoginActivity)
            .addOnCompleteListener { }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK)
                open()  // Successfully signed in
            else
                if(response!=null)
                    Toast.makeText(this@LoginActivity,"Error $response.error!!.errorCode : $response.error!!.message" , Toast.LENGTH_SHORT).show()
        }
    }

    private fun open() {
        startActivity(Intent( this,  MainActivity::class.java))
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
    }
}