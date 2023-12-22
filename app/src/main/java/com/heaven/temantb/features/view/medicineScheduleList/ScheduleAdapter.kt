package com.heaven.temantb.features.view.medicineScheduleList

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.heaven.temantb.R
import com.heaven.temantb.databinding.ItemRowScheduleBinding
import com.heaven.temantb.features.data.pref.retrofit.response.ListScheduleItem
import com.heaven.temantb.features.view.medicineScheduleDetail.MedicineScheduleDetailActivity

@Suppress("DEPRECATION")
class ScheduleAdapter(
    private var listOfSchedule: List<ListScheduleItem>,
    private val token: String,
    private val nearestHourIndex: Int,
    private val viewModel: ScheduleListViewModel
) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemRowScheduleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule = listOfSchedule[position]
        val token = token

        holder.binding.apply {
            tvItemMedicineName.text = schedule.medicineName
            tvItemDescription.text = if (schedule.description.length > 24) {
                "${schedule.description.substring(0, 24)}..."
            } else {
                schedule.description
            }

            tvItemHour.text = schedule.hour

            if (position == nearestHourIndex) {
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.blue3))
            } else {
                root.setBackgroundColor(ContextCompat.getColor(root.context, android.R.color.transparent))
            }

            root.setOnClickListener {
                val intent = Intent(root.context, MedicineScheduleDetailActivity::class.java)
                intent.putExtra(MedicineScheduleDetailActivity.EXTRA_ID, schedule.scheduleId)
                intent.putExtra(MedicineScheduleDetailActivity.EXTRA_TOKEN, token)

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

    fun removeItem(position: Int) {
        val updatedList = listOfSchedule.toMutableList()
        updatedList.removeAt(position)
        listOfSchedule = updatedList
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }


    inner class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val scheduleId = listOfSchedule[position].scheduleId
            viewModel.deleteSchedule(token, scheduleId)

            removeItem(position)
        }
    }
}