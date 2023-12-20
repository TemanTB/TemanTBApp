package com.heaven.temantb.features.view.healthMonitorList

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.heaven.temantb.databinding.ItemRowHealthBinding
import com.heaven.temantb.features.data.pref.retrofit.response.ListHealthItem
import com.heaven.temantb.features.view.healthMonitorDetail.HealthMonitorDetailActivity
import com.heaven.temantb.features.view.medicineScheduleDetail.MedicineScheduleDetailActivity

class HealthAdapter(
    private var listOfHealth: List<ListHealthItem>,
    private val token: String,
    private val viewModel: HealthListViewModel
) : RecyclerView.Adapter<HealthAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemRowHealthBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowHealthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val health = listOfHealth[position]
        val token = token

        holder.binding.apply {
            tvItemDescription.text = health.description
            tvItemDate.text = health.date
            tvItemAverage.text = health.average

            root.setOnClickListener {
                val intent = Intent(root.context, HealthMonitorDetailActivity::class.java)
                intent.putExtra(MedicineScheduleDetailActivity.EXTRA_ID, health.healthId)
                intent.putExtra(MedicineScheduleDetailActivity.EXTRA_TOKEN, token)

                val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    root.context as Activity,
                    Pair(tvItemDescription, "trDescription"),
                    Pair(tvItemDate, "trDate"),
                    Pair(tvItemAverage, "trAverage")
                )
                root.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    override fun getItemCount(): Int = listOfHealth.size

    fun removeItem(position: Int) {
        val updatedList = listOfHealth.toMutableList()
        updatedList.removeAt(position)
        listOfHealth = updatedList
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
            val scheduleId = listOfHealth[position].healthId
            viewModel.deleteHealth(token, scheduleId)
            Log.d("Delete Schedule", "Token: $token, ScheduleId: $scheduleId")

            removeItem(position)
        }
    }
}