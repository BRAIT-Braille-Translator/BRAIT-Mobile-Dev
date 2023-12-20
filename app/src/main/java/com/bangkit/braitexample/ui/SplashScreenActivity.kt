package com.bangkit.braitexample.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.braitexample.R
import com.bangkit.braitexample.databinding.SplashScreenActivityBinding
import com.bangkit.braitexample.viewmodel.SplashViewModel
import com.bangkit.braitexample.viewmodel.ViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: SplashScreenActivityBinding

    private val viewModel by viewModels<SplashViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getSession().observe(this) { user ->
                if (user.token == "") {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    startActivity(Intent(this,MainActivity::class.java))
                }
                finishAffinity()
            }
        }, DELAY_TIME)
    }

    companion object {
        const val DELAY_TIME = 1000L
    }
}