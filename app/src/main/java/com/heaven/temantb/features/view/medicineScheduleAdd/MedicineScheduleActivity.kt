package com.heaven.temantb.features.view.medicineScheduleAdd

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.heaven.temantb.R
import com.heaven.temantb.databinding.ActivityMedicineScheduleBinding
import com.heaven.temantb.features.alarm.AlarmReceiver
import com.heaven.temantb.features.data.di.AlertIndicator
import com.heaven.temantb.features.view.ViewModelFactory
import com.heaven.temantb.features.view.medicineScheduleList.ScheduleListActivity
import com.heaven.temantb.util.AppPreferences
import com.heaven.temantb.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MedicineScheduleActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var binding: ActivityMedicineScheduleBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private val viewModel by viewModels<MedicineScheduleViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 33 && !isNotificationPermissionGranted()) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }


        binding = ActivityMedicineScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra(EXTRA_TOKEN)
        val userId = intent.getStringExtra(EXTRA_USER_ID)

        binding.saveButton.setOnClickListener {
            if (token != null && userId != null) {
                Log.d("ButtonClicked", "Button clicked")
                uploadMedicineSchedule(token, userId)
            }
        }

        // Initialize the class variable
        alarmReceiver = AlarmReceiver()
    }


    @RequiresApi(Build.VERSION_CODES.S)
    private fun uploadMedicineSchedule(token: String, userId: String) {
        val medicineName = binding.medicineNameEditText.text.toString()
        val description = binding.descReminderEditText.text.toString()
        val hour = binding.tvHour.text.toString()

        if (medicineName.isEmpty() || description.isEmpty() || hour.isEmpty()) {
            showToast("Please fill in all the fields.")
            return
        }

        if (isNotificationEnabled()) {
            alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_TEMANTB, hour, description, medicineName)

            Log.d(
                "Ini logd medicineact",
                "Medicine Name: $medicineName, Description: $description, Hour: $hour"
            )
            viewModel.uploadMedicineSchedule(token, medicineName, description, hour, userId)
                .observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            AlertIndicator.Loading -> {
                                showLoading(true)
                            }

                            is AlertIndicator.Success -> {
                                showLoading(false)
                                AlertDialog.Builder(this).apply {
                                    setTitle("Yay!")
                                    setMessage(result.data.message)
                                    setPositiveButton("Ok") { _, _ ->
                                        val intent = Intent(context, ScheduleListActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }

                            is AlertIndicator.Error -> {
                                showLoading(false)
                                AlertDialog.Builder(this).apply {
                                    setTitle("Ups!")
                                    setMessage(result.error)
                                    setPositiveButton("Ok") { _, _ ->
                                        val intent = Intent(context, ScheduleListActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }

                            else -> {}
                        }
                    }
                }
        } else {
            showToast(getString(R.string.please_enable_notifications_in_settings))
        }
    }

    private fun showToast(description: String) {
        Toast.makeText(this, description, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun buttonTimePickerHour(view: View) {
        showTimePicker("hourPicker")
    }

    private fun showTimePicker(tag: String) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, tag)
    }

    private fun isNotificationEnabled(): Boolean {
        val sharedPreferences = getSharedPreferences(
            AppPreferences.PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(
            AppPreferences.PREF_KEY_NOTIFICATION_ENABLED,
            AppPreferences.DEFAULT_NOTIFICATION_ENABLED
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNotificationPermissionGranted(): Boolean {
        return checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    }
    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        when (tag) {
            "hourPicker" -> binding.tvHour.text = timeFormat.format(calendar.time)
        }
    }

    companion object {
        const val EXTRA_TOKEN = "extra_token"
        const val EXTRA_USER_ID = "extra_user_id"
    }

}