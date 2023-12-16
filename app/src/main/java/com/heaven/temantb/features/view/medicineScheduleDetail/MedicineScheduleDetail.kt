package com.heaven.temantb.features.view.medicineScheduleDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.heaven.temanTB.R

class MedicineScheduleDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_schedule_detail)
    }

    companion object {
        const val EXTRA_ID: String = "extra_id"
        const val EXTRA_TOKEN: String = "extra_token"
    }
}