package com.bangkit.braitexample.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bangkit.braitexample.data.model.User
import com.bangkit.braitexample.data.response.Result
import com.bangkit.braitexample.databinding.LoginActivityBinding
import com.bangkit.braitexample.viewmodel.LoginViewModel
import com.bangkit.braitexample.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.login.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.postLogin(email, password).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        Toast.makeText(this, "Login ${result.data.message}", Toast.LENGTH_SHORT)
                            .show()
                        val response = result.data
                        viewModel.saveSession(User(response.data.accessToken))
                    }
                    is Result.Error -> {
                        Toast.makeText(this, "Login ${result.error}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        binding.createAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}