package com.heaven.temantb.login.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.heaven.temanTB.databinding.ActivityMainBinding
import com.heaven.temantb.login.view.ViewModelFactory
import com.heaven.temantb.login.view.health_monitor.HealthMonitorActivity
import com.heaven.temantb.login.view.medicineSchedule.MedicineScheduleActivity
import com.heaven.temantb.login.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                setupView()
                setupAction(user.token)
            }
            playAnimation()
        }
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
    }

    private fun setupAction(token: String) {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
        binding.medicineSchedule.setOnClickListener{
            val addIntent = Intent(this, MedicineScheduleActivity::class.java)
            addIntent.putExtra(MedicineScheduleActivity.EXTRA_TOKEN, token)
            startActivity(addIntent)
        }
        binding.healthMonitor.setOnClickListener{
            val addIntent = Intent(this, HealthMonitorActivity::class.java)
            startActivity(addIntent)
        }
    }

    private fun playAnimation() {
//        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()

//        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
//        val message = ObjectAnimator.ofFloat(binding.tvSubMessageRegister, View.ALPHA, 1f).setDuration(100)
        val logout = ObjectAnimator.ofFloat(binding.logoutButton, View.ALPHA, 1f).setDuration(100)
        val medicineSchedule = ObjectAnimator.ofFloat(binding.medicineSchedule, View.ALPHA, 1f).setDuration(100)
        val healthMonitor = ObjectAnimator.ofFloat(binding.healthMonitor, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                medicineSchedule,
                healthMonitor,
                logout
            )
//            playSequentially(name, message, logout)
            startDelay = 100
        }.start()
    }
}