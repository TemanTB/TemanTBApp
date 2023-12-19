package com.heaven.temantb.features.view.medicineScheduleList

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.heaven.temantb.databinding.ActivityScheduleListBinding
import com.heaven.temantb.features.data.di.AlertIndicator
import com.heaven.temantb.features.data.pref.retrofit.response.ListScheduleItem
import com.heaven.temantb.features.view.ViewModelFactory
import com.heaven.temantb.features.view.medicineScheduleAdd.MedicineScheduleActivity

class ScheduleListActivity : AppCompatActivity() {
    private val viewModel by viewModels<ScheduleListViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityScheduleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, ScheduleListActivity::class.java))
                finish()
            } else {
                setupView(user.token, user.userID)
            }
            setupAction(user.token, user.userID)
        }
    }

    private fun setupView(token: String, userID: String) {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        viewModel.getSchedule(token, userID).observe(this) { alert ->
            Log.d("ScheduleListActivity1", "Get Schedule called")
            if (alert != null) {
                when (alert) {
                    AlertIndicator.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is AlertIndicator.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(this, alert.error, Toast.LENGTH_SHORT).show()
                    }
                    is AlertIndicator.Success -> {
                        binding.progressBar.isVisible = false

                        if (alert.data.listSchedule.isEmpty()) {
                            Log.d("ScheduleListActivity2", "success empty")
                            binding.noStoriesTextView.visibility = View.VISIBLE
                            binding.rvStories.isVisible = false
                        } else {
                            Log.d("ScheduleListActivity3", "success not empty")
                            binding.noStoriesTextView.visibility = View.GONE
                            binding.rvStories.layoutManager = LinearLayoutManager(this)
                            binding.rvStories.adapter = triggerRecyclerView(alert.data.listSchedule, token, userID)
                        }
                    }
                }
            }
        }
    }

    private fun triggerRecyclerView(list: List<ListScheduleItem>, token: String, userID: String): ScheduleAdapter =
        ScheduleAdapter(list, token, userID)

    private fun setupAction(token: String, userID: String) {
        binding.addSchedule.setOnClickListener {
            val intent = Intent(this, MedicineScheduleActivity::class.java)
            intent.putExtra(MedicineScheduleActivity.EXTRA_TOKEN, token)
            intent.putExtra(MedicineScheduleActivity.EXTRA_USER_ID, userID)
            startActivity(intent)
        }
    }
}
