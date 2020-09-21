package ru.trinitydigital.fitnes.ui.onboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_on_board.*
import ru.trinitydigital.fitnes.R
import ru.trinitydigital.fitnes.data.local.PreferenceHelper
import ru.trinitydigital.fitnes.ui.main.MainActivity
import ru.trinitydigital.fitnes.utils.launchActivity

class OnBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board)
        setupListeners()
    }

    private fun setupListeners() {
        btnNext.setOnClickListener {
            PreferenceHelper.setIsFirstLaunch()
            launchActivity<MainActivity>()
        }
    }
}