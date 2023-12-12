package com.heaven.temantb.login.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.heaven.storyapp.view.data.di.AlertIndicator
import com.heaven.temanTB.R
import com.heaven.temanTB.databinding.ActivityLoginBinding
import com.heaven.temantb.login.view.ViewModelFactory
import com.heaven.temantb.login.view.main.MainActivity
import com.heaven.temantb.login.view.signup.SignupActivity


class LoginActivity : AppCompatActivity() {
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            loginViewModel.login(email, password).observe(this) { result ->
                if (result != null) {
                    when(result) {
                        AlertIndicator.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        is AlertIndicator.Success -> {
                            binding.progressBar.isVisible = false
                            AlertDialog.Builder(this).apply {
                                setTitle("Yay!")
                                setMessage(getString(R.string.login_successful_message))
                                setPositiveButton("Ok") { _, _ ->
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }
                        is AlertIndicator.Error -> {
                            binding.progressBar.isVisible = false
                            AlertDialog.Builder(this).apply {
                                setTitle("Oops!")
                                setMessage(getString(R.string.login_failed_message))
                                setPositiveButton("Ok") { _, _ ->
                                    binding.emailEditText.text?.clear()
                                    binding.passwordEditText.text?.clear()
                                    binding.emailEditText.requestFocus()
                                }
                                create()
                                show()
                            }
                        }
                    }
                }
            }
        }

        binding.tvHaveAccount.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
        val alreadyRegister = ObjectAnimator.ofFloat(binding.tvHaveAccount, View.ALPHA, 1f).setDuration(100)
        val msgLogin = ObjectAnimator.ofFloat(binding.tvSubMessageLogin, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login,
                alreadyRegister,
                msgLogin
            )
            startDelay = 100
        }.start()
    }

}
