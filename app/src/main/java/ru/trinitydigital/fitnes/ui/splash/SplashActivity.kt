package ru.trinitydigital.fitnes.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ru.trinitydigital.fitnes.R
import ru.trinitydigital.fitnes.data.local.PreferenceHelper
import ru.trinitydigital.fitnes.ui.main.MainActivity
import ru.trinitydigital.fitnes.ui.onboard.OnBoardActivity
import ru.trinitydigital.fitnes.utils.launchActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            selectActivity()
        } , 3000)
    }

    private fun selectActivity() {
        if (PreferenceHelper.getIsFirstLaunch()) {
            launchActivity<OnBoardActivity>()
            finish()
        } else {
            launchActivity<MainActivity>()
            finish()
        }
    }
}