package com.bangkit.braitexample.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bangkit.braitexample.data.response.Result
import com.bangkit.braitexample.databinding.DaftarActivityBinding
import com.bangkit.braitexample.viewmodel.RegisterViewModel
import com.bangkit.braitexample.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: DaftarActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DaftarActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.login.setOnClickListener {
            val name = binding.etNama.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.postRegister(name, email, password).observe(this) { result ->
                when (result) {

                    is Result.Loading -> {
                        binding.loadingProgressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.loadingProgressBar.visibility = View.GONE
                        Toast.makeText(this, "Daftar ${result.data.message}", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    }

                    is Result.Error -> {
                        binding.loadingProgressBar.visibility = View.GONE
                        Toast.makeText(this, "Daftar ${result.error}", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }
}