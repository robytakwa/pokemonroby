package com.codeid.pokemon.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import com.codeid.pokemon.MainActivity
import com.codeid.pokemon.databinding.ActivityLoginBinding
import com.codeid.pokemon.presentation.common.ViewModelFactory
import com.codeid.pokemon.presentation.detail.DetailViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val name = sharedPref.getString("name", null)
        val email = sharedPref.getString("email", null)

        if (!name.isNullOrBlank() && !email.isNullOrBlank()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        viewModel = ViewModelFactory.inject(this).create(LoginViewModel::class.java)


        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            lifecycleScope.launch {
                val user = viewModel.login(this@LoginActivity, email, password)
                if (user != null) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Login gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, com.codeid.pokemon.presentation.register.RegisterActivity::class.java))
        }
    }
}
