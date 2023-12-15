package com.heaven.temantb.login.view.medicineSchedule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.heaven.temanTB.R
import com.heaven.temanTB.databinding.ActivityMedicineScheduleBinding
import com.heaven.temantb.login.data.di.AlertIndicator
import com.heaven.temantb.login.view.ViewModelFactory
import com.heaven.temantb.login.view.main.MainActivity
import com.heaven.temantb.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MedicineScheduleActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var binding: ActivityMedicineScheduleBinding
    private val viewModel by viewModels<MedicineScheduleViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra(EXTRA_TOKEN)

        binding.saveButton.setOnClickListener {
            if (token != null) {
                Log.d("ButtonClicked", "Button clicked")
                uploadMedicineSchedule(token)
            }
        }
    }

    private fun uploadMedicineSchedule(token: String) {
        val medicineName = binding.medicineNameEditText.text.toString()
        val description = binding.descReminderEditText.text.toString()
        val hour = binding.tvHour.text.toString()

        Log.d("Ini logd medicineact", "Medicine Name: $medicineName, Description: $description, Hour: $hour")
        viewModel.uploadMedicineSchedule(token, medicineName, description, hour).observe(this) { result ->
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
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
    companion object {
        const val EXTRA_TOKEN = "extra_token"
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            "hourPicker" -> findViewById<TextView>(R.id.tv_hour).text = timeFormat.format(calendar.time)
        }
    }
}