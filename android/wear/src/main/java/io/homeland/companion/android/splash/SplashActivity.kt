package io.homeland.companion.android.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.homeland.companion.android.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = HomeActivity.newInstance(this)
        startActivity(intent)
        finish()
    }
}
