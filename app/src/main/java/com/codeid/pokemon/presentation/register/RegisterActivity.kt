package com.codeid.pokemon.presentation.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.codeid.pokemon.databinding.ActivityRegisterBinding
import com.codeid.pokemon.domain.model.User
import com.codeid.pokemon.presentation.common.ViewModelFactory
import com.codeid.pokemon.presentation.login.LoginViewModel
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelFactory.inject(this).create(RegisterViewModel::class.java)

        binding.btnSubmit.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                val user = User(username = username, email = email, password = password)
                lifecycleScope.launch {
                    viewModel.register(user)
                    Toast.makeText(this@RegisterActivity, "Register berhasil", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
