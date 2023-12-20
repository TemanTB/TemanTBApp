package com.heaven.temantb.features.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.heaven.temantb.R
import com.heaven.temantb.databinding.ActivitySignupBinding
import com.heaven.temantb.features.data.di.AlertIndicator
import com.heaven.temantb.features.view.ViewModelFactory
import com.heaven.temantb.features.view.login.LoginActivity


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signUpViewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        playAnimation()

        binding.passwordEditText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length >= 8) {
                    binding.signUpButton.isEnabled = true
                    binding.passwordEditText.error = null
                    binding.passwordEditTextLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                } else if(s.isEmpty()){
                    binding.signUpButton.isEnabled = false
                    binding.passwordEditText.error = getString(R.string.required)
                } else{
                    binding.passwordEditText.setError(getString(R.string.msg_error_password),null)
                    binding.passwordEditTextLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                }
            }

            override fun afterTextChanged(s: Editable) {
                setupAction()
            }
        })
    }

    private fun setupAction() {
        binding.haveAccountButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.signUpButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confPassword = binding.confPasswordEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()

            signUpViewModel.signUp(name, email, phone, password, confPassword).observe(this) { result ->
                if (result != null) {
                    when(result) {
                        AlertIndicator.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        is AlertIndicator.Success -> {
                            binding.progressBar.isVisible = false
                            AlertDialog.Builder(this).apply {
                                setTitle("Yay!")
                                setMessage(getString(R.string.account_ready_message))
                                setPositiveButton("Next") { _, _ ->
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
                                setMessage(getString(R.string.account_not_ready_message))
                                setPositiveButton("Ok") { _, _ ->
                                    binding.nameEditText.requestFocus()
                                }
                                create()
                                show()
                            }
                        }
                    }
                }
            }
        }
    }
    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val tvSubMessageRegister = ObjectAnimator.ofFloat(binding.tvSubMessageRegister, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val confPasswordView =
            ObjectAnimator.ofFloat(binding.confPasswordTextView, View.ALPHA, 1f).setDuration(100)
        val confPasswordEditTextLayout =
            ObjectAnimator.ofFloat(binding.confPasswordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val phoneTextView =
            ObjectAnimator.ofFloat(binding.phoneTextView, View.ALPHA, 1f).setDuration(100)
        val phoneEditTextLayout =
            ObjectAnimator.ofFloat(binding.phoneEditTextLayout, View.ALPHA, 1f).setDuration(100)

        val signup = ObjectAnimator.ofFloat(binding.signUpButton, View.ALPHA, 1f).setDuration(100)
        val alreadyRegister = ObjectAnimator.ofFloat(binding.haveAccountButton, View.ALPHA, 1f).setDuration(100)



        AnimatorSet().apply {
            playSequentially(
                title,
                tvSubMessageRegister,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                confPasswordView,
                confPasswordEditTextLayout,
                phoneTextView,
                phoneEditTextLayout,
                signup,
                alreadyRegister
            )
            startDelay = 100
        }.start()
    }

}