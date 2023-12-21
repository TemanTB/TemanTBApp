package com.heaven.temantb.features.view.healthMonitorDetail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.heaven.temantb.databinding.ActivityHealthMonitorDetailBinding
import com.heaven.temantb.features.data.di.AlertIndicator
import com.heaven.temantb.features.view.ViewModelFactory

class HealthMonitorDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHealthMonitorDetailBinding
    private val detailHealthViewModel by viewModels<DetailHealthViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthMonitorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val healthId = intent.getStringExtra(EXTRA_ID)
        val token = intent.getStringExtra(EXTRA_TOKEN)


        Log.d("cekTokenNIdHealth", "ID: $healthId, Token: $token")
        Log.d("cekTokenNIdHealth", "ID: $healthId, Token: $token")

        if (token != null && healthId != null) {
            setupView(healthId, token)
        }
    }

    private fun setupView(healthId: String, token: String) {
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
                    val healthInDetailList = alert.data.data

                    healthInDetailList.let {
                        binding.apply {
                            tvDeMoDescription.text = it.description
                            tvDeMoDate.text = it.date
                            tvDeMoAverage.text = it.average

                            Glide.with(root.context)
                                .load(it.images)
                                .into(ivFlag)
                        }
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
