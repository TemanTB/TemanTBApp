package com.heaven.temantb.features.view.healthMonitorDetail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.heaven.temantb.databinding.ActivityHealthMonitorDetailBinding
import com.heaven.temantb.features.data.di.AlertIndicator
import com.heaven.temantb.features.view.ViewModelFactory
import com.heaven.temantb.features.view.medicineScheduleDetail.DetailHealthViewModel

class HealthMonitorDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHealthMonitorDetailBinding
    private val detailHealthViewModel by viewModels<DetailHealthViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthMonitorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val healthID = intent.getStringExtra(EXTRA_ID)
        val token = intent.getStringExtra(EXTRA_TOKEN)


        Log.d("cekTokenNIdHealth", "ID: $healthID, Token: $token")
        Log.d("cekTokenNIdHealth", "ID: $healthID, Token: $token")

        if (token != null && healthID != null) {
            setupView(healthID, token)
        }
    }

    private fun setupView(healthId: String, token: String) {
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

        detailHealthViewModel.getDetailHealth(healthId, token).observe(this) { alert ->
            when (alert) {
                AlertIndicator.Loading -> binding.progressBar.isVisible = true
                is AlertIndicator.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, alert.error, Toast.LENGTH_SHORT).show()
                }
                is AlertIndicator.Success -> {
                    binding.progressBar.isVisible = false
                    val healthInDetail = alert.data.data.firstOrNull()

                    healthInDetail?.let {
                        binding.apply {
                            tvDeMoDescription.text = it.description
                            tvDeMoDate.text = it.date
                            tvDeMoAverage.text = it.average

//                            Log.d(
//                                "cekTokenNIdSchedule2",
//                                "MedicineName: ${heal.text}, Description: ${tvDescription.text}, Hour: ${tvTime.text}"
//                            )
                        }
                    } ?: run {
                        Log.d("Data", "No data found")
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_ID: String = "extra_id"
        const val EXTRA_TOKEN: String = "extra_token"
    }
}
