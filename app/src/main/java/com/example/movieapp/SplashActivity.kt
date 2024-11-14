package com.example.movieapp


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            checkUserStatus()
        }, 2000) // 2-second delay for splash screen
    }

    private fun checkUserStatus() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            // User is not logged in, navigate to AuthActivity
            startActivity(Intent(this, AuthActivity::class.java))
        } else {
            // User is already logged in, navigate to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}
