package mchou.apps.chat

import android.animation.*
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import kotlinx.android.synthetic.main.activity_launch.*


class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        btnChat.alpha = 0f
        startAnimation()
        supportActionBar!!.hide()
    }

    private fun startAnimation() {
        val animatorSet = AnimatorSet()
        val fadeAnim: ValueAnimator = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f)
        fadeAnim.duration = 2500
        fadeAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                //((ObjectAnimator)animation).getTarget();
                animateLogo()
            }
        })
        animatorSet.play(fadeAnim)
        animatorSet.start()
    }

    private fun animateLogo() {
        val finishingConstraintSet = ConstraintSet()
        finishingConstraintSet.clone(applicationContext, R.layout.activity_launch_final)
        TransitionManager.beginDelayedTransition(root)
        finishingConstraintSet.applyTo(root)

        logo!!.setImageDrawable(resources.getDrawable(R.drawable.chat_logo_b, null))

        val fade1Anim: ValueAnimator =  ObjectAnimator.ofFloat(btnChat, "alpha", 0f, 1f).setDuration(1500)
        //val fade2Anim: ValueAnimator =  ObjectAnimator.ofFloat(btnFirebaseUI, "alpha", 0f, 1f).setDuration(200)
        //val fade3Anim: ValueAnimator =  ObjectAnimator.ofFloat(btnRealtimeDB, "alpha", 0f, 1f).setDuration(400)
        val animatorSet = AnimatorSet()

        animatorSet.play(fade1Anim) //.before(fade2Anim).before(fade3Anim)
        animatorSet.start()
    }

    fun open(view: View) {

        val activityClass : Class<*> = LoginActivity::class.java
        val intent = Intent( this,  activityClass)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);

    }


}