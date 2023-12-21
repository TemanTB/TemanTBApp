package com.heaven.temantb.features.view.healthMonitorList

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.heaven.temantb.databinding.ActivityHealthListBinding
import com.heaven.temantb.features.data.di.AlertIndicator
import com.heaven.temantb.features.data.pref.retrofit.response.ListHealthItem
import com.heaven.temantb.features.view.ViewModelFactory
import com.heaven.temantb.features.view.healthMonitorAdd.HealthMonitorActivity
import com.heaven.temantb.features.view.medicineScheduleAdd.MedicineScheduleActivity

class HealthListActivity : AppCompatActivity() {

    private val viewModel by viewModels<HealthListViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityHealthListBinding
    private lateinit var healthAdapter: HealthAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHealthListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, HealthListActivity::class.java))
                finish()
            } else {
                setupView(user.token, user.userId)
            }
            setupAction(user.token, user.userId)
        }
    }

    private fun sortHealthListByDate(listHealth: List<ListHealthItem>): List<ListHealthItem> {
        return listHealth.sortedByDescending { it.date }
    }

    private fun setupView(token: String, userId: String) {
        viewModel.getHealth(token, userId).observe(this) { alert ->
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

                        if (alert.data.listHealth.isEmpty()) {
                            binding.noHealthTextView.visibility = View.VISIBLE
                            binding.rvHealth.isVisible = false
                        } else {
                            binding.noHealthTextView.visibility = View.GONE

                            val sortedList = sortHealthListByDate(alert.data.listHealth)

                            healthAdapter = HealthAdapter(sortedList, token, viewModel)

                            binding.rvHealth.layoutManager = LinearLayoutManager(this)
                            binding.rvHealth.adapter = healthAdapter

                            val itemTouchHelper = ItemTouchHelper(healthAdapter.SwipeToDeleteCallback())
                            itemTouchHelper.attachToRecyclerView(binding.rvHealth)
                        }
                    }
                }
            }
        }
    }

    private fun setupAction(token: String, userId: String) {
        binding.addHealth.setOnClickListener {
            val intent = Intent(this, HealthMonitorActivity::class.java)
            intent.putExtra(MedicineScheduleActivity.EXTRA_TOKEN, token)
            intent.putExtra(MedicineScheduleActivity.EXTRA_USER_ID, userId)
            startActivity(intent)
        }
    }
}
