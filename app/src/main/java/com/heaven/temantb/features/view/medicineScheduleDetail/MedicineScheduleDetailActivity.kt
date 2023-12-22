package com.heaven.temantb.features.view.medicineScheduleDetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.heaven.temantb.databinding.ActivityMedicineScheduleDetailBinding
import com.heaven.temantb.features.data.di.AlertIndicator
import com.heaven.temantb.features.view.ViewModelFactory

class MedicineScheduleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicineScheduleDetailBinding
    private val detailScheduleViewModel by viewModels<DetailScheduleViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineScheduleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scheduleID = intent.getStringExtra(EXTRA_ID)
        val token = intent.getStringExtra(EXTRA_TOKEN)

        if (token != null && scheduleID != null) {
            setupView(scheduleID, token)
        }
    }

    private fun setupView(scheduleID: String, token: String) {
        detailScheduleViewModel.getDetailSchedule(scheduleID, token).observe(this) { alert ->
            when (alert) {
                AlertIndicator.Loading -> binding.progressBar.isVisible = true
                is AlertIndicator.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, alert.error, Toast.LENGTH_SHORT).show()
                }
                is AlertIndicator.Success -> {
                    binding.progressBar.isVisible = false
                    val scheduleInDetail = alert.data.data.firstOrNull()

                    scheduleInDetail?.let {
                        binding.apply {
                            tvMedicineName.text = it.medicineName
                            tvDescription.text = it.description
                            tvTime.text = it.hour
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
