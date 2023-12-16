package com.heaven.temantb.features.view.medicineScheduleList

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.heaven.temanTB.databinding.ItemRowScheduleBinding
import com.heaven.temantb.features.data.pref.retrofit.response.ListScheduleItem
import com.heaven.temantb.features.view.medicineScheduleDetail.MedicineScheduleDetail

class ScheduleAdapter(private val listOfSchedule: List<ListScheduleItem>, private val token: String) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemRowScheduleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule = listOfSchedule[position]
        val token = token

        holder.binding.apply {
            tvItemMedicineName.text = schedule.medicineName
            tvItemDescription.text = schedule.description
            tvItemHour.text = schedule.hour

            root.setOnClickListener {
                val intent = Intent(root.context, MedicineScheduleDetail::class.java)
                intent.putExtra(MedicineScheduleDetail.EXTRA_ID, schedule.scheduleID)
                intent.putExtra(MedicineScheduleDetail.EXTRA_TOKEN, token)

                val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    root.context as Activity,
                    Pair(tvItemMedicineName, "trMedicineName"),
                    Pair(tvItemDescription, "trDescription"),
                    Pair(tvItemHour, "trHour")
                )

                root.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    override fun getItemCount(): Int = listOfSchedule.size
}
