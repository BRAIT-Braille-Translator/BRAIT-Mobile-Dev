package com.bangkit.braitexample.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bangkit.braitexample.data.response.Result
import com.bangkit.braitexample.databinding.ProfileActivityBinding
import com.bangkit.braitexample.viewmodel.ProfileViewModel
import com.bangkit.braitexample.viewmodel.ViewModelFactory

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ProfileActivityBinding

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ProfileActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAction()

    }

    private fun setupAction() {
        viewModel.getSession().observe(this) {user ->
            if (user.token == "") {
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            } else {
                showProfile()
            }
        }
        logout()
    }

    private fun showProfile() {
        viewModel.getProfileUser().observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    val username = result.data.data.username
                    val email = result.data.data.email
                    binding.username.text = username
                    binding.tvUserEmail.text = email
                }
                is Result.Error -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    private fun logout() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }
}