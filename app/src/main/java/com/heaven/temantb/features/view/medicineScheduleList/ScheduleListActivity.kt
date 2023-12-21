package com.heaven.temantb.features.view.medicineScheduleList

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.heaven.temantb.databinding.ActivityScheduleListBinding
import com.heaven.temantb.features.data.di.AlertIndicator
import com.heaven.temantb.features.data.pref.retrofit.response.ListScheduleItem
import com.heaven.temantb.features.view.ViewModelFactory
import com.heaven.temantb.features.view.medicineScheduleAdd.MedicineScheduleActivity
import java.util.Calendar

class ScheduleListActivity : AppCompatActivity() {

    private val viewModel by viewModels<ScheduleListViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityScheduleListBinding
    private lateinit var scheduleAdapter: ScheduleAdapter
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScheduleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, ScheduleListActivity::class.java))
                finish()
            } else {
                setupView(user.token, user.userId)
            }
            setupAction(user.token, user.userId)
        }

        handler.post(object : Runnable {
            override fun run() {
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun setupView(token: String, userId: String) {
        viewModel.getSchedule(token, userId).observe(this) { alert ->
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
                            binding.noStoriesTextView.visibility = View.VISIBLE
                            binding.rvStories.isVisible = false
                            binding.ivEmpty.visibility = View.VISIBLE
                            binding.textHi.visibility = View.INVISIBLE
                            binding.textName.visibility = View.INVISIBLE
                            binding.ivNoMore.visibility = View.INVISIBLE

                        } else {
                            binding.ivEmpty.visibility = View.GONE
                            binding.noStoriesTextView.visibility = View.GONE
                            binding.textHi.visibility = View.VISIBLE
                            binding.textName.visibility = View.VISIBLE
                            binding.ivNoMore.visibility = View.VISIBLE

                            val nearestHourIndex = findNearestHourIndex(alert.data.listSchedule)

                            scheduleAdapter = ScheduleAdapter(alert.data.listSchedule, token, nearestHourIndex, viewModel)
                            binding.rvStories.layoutManager = LinearLayoutManager(this)
                            binding.rvStories.adapter = scheduleAdapter

                            val itemTouchHelper = ItemTouchHelper(scheduleAdapter.SwipeToDeleteCallback())
                            itemTouchHelper.attachToRecyclerView(binding.rvStories)
                        }
                    }
                }
            }
        }
    }

    private fun setupAction(token: String, userId: String) {
        binding.addSchedule.setOnClickListener {
            val intent = Intent(this, MedicineScheduleActivity::class.java)
            intent.putExtra(MedicineScheduleActivity.EXTRA_TOKEN, token)
            intent.putExtra(MedicineScheduleActivity.EXTRA_USER_ID, userId)
            startActivity(intent)
        }
    }

    private fun findNearestHourIndex(listOfSchedule: List<ListScheduleItem>): Int {
        val currentTime = Calendar.getInstance().timeInMillis

        for ((index, scheduleItem) in listOfSchedule.withIndex()) {
            val scheduleTime = getScheduleTimeInMillis(scheduleItem.hour)

            if (scheduleTime > currentTime) {
                return index
            }
        }

        return listOfSchedule.size - 1
    }

    private fun getScheduleTimeInMillis(scheduleTime: String): Long {
        val calendar = Calendar.getInstance()

        val timeParts = scheduleTime.split(":")
        calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
        calendar.set(Calendar.MINUTE, timeParts[1].toInt())

        return calendar.timeInMillis
    }
}