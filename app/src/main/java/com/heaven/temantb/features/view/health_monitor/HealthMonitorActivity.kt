package com.heaven.temantb.features.view.health_monitor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.heaven.temantb.R

class HealthMonitorActivity : AppCompatActivity() {
    private lateinit var binding: HealthMonitorActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_monitor)
    }
}