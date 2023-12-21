package com.heaven.temantb.features.view.healthMonitorAdd

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.heaven.temantb.databinding.ActivityHealthMonitorBinding
import com.heaven.temantb.features.data.di.AlertIndicator
import com.heaven.temantb.features.view.ViewModelFactory
import com.heaven.temantb.features.view.main.MainActivity

class HealthMonitorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHealthMonitorBinding
    private val viewModel by viewModels<HealthMonitorViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthMonitorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra(EXTRA_TOKEN)
        val userId = intent.getStringExtra(EXTRA_USER_ID)

        binding.saveButton.setOnClickListener {
            if (token != null && userId != null) {
                Log.d("ButtonClicked", "Button clicked")
                uploadHealth(token, userId)
            }
        }
    }

    private fun uploadHealth(token: String, userId: String) {
        val description = binding.descriptionEditText.text.toString()

        if (description.isEmpty()) {
            showToast("Please input your symptoms first.")
            return
        }

        viewModel.uploadHealth(token, description, userId).observe(this) { result ->
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
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(description: String) {
        Toast.makeText(this, description, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_TOKEN = "extra_token"
        const val EXTRA_USER_ID = "extra_user_id"
    }
}
