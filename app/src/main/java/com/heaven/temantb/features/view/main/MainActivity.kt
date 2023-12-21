package com.heaven.temantb.features.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.heaven.temantb.databinding.ActivityMainBinding
import com.heaven.temantb.features.view.ViewModelFactory
import com.heaven.temantb.features.view.healthMonitorAdd.HealthMonitorActivity
import com.heaven.temantb.features.view.healthMonitorList.HealthListActivity
import com.heaven.temantb.features.view.medicineScheduleAdd.MedicineScheduleActivity
import com.heaven.temantb.features.view.medicineScheduleList.ScheduleListActivity
import com.heaven.temantb.features.view.welcome.WelcomeActivity

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
                setupAction(user.token, user.userId)

                // Check if loginResult is not null before accessing its properties
                val greetingText = if (user. != null) {
                    "Hello, ${user.name}"
                } else {
                    // Handle the case when loginResult is null
                    "Hello, Guest"
                }

                binding.greetingText.text = greetingText

                Log.d("MainActivityCek", "token: ${user.token}, userId: ${user.userId}")
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

    private fun setupAction(token: String, userId: String) {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
        binding.medicineSchedule.setOnClickListener{
            val addIntent = Intent(this, ScheduleListActivity::class.java)

            addIntent.putExtra(MedicineScheduleActivity.EXTRA_TOKEN, token)
            addIntent.putExtra(MedicineScheduleActivity.EXTRA_USER_ID, userId)
            startActivity(addIntent)
        }
        binding.healthMonitor.setOnClickListener{
            val addHealth = Intent(this, HealthListActivity::class.java)

            addHealth.putExtra(HealthMonitorActivity.EXTRA_TOKEN, token)
            addHealth.putExtra(HealthMonitorActivity.EXTRA_USER_ID, userId)
            startActivity(addHealth)
        }
    }

    private fun playAnimation() {
        val logout = ObjectAnimator.ofFloat(binding.logoutButton, View.ALPHA, 1f).setDuration(100)
        val medicineSchedule = ObjectAnimator.ofFloat(binding.medicineSchedule, View.ALPHA, 1f).setDuration(100)
        val healthMonitor = ObjectAnimator.ofFloat(binding.healthMonitor, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                medicineSchedule,
                healthMonitor,
                logout
            )
            startDelay = 100
        }.start()
    }
}