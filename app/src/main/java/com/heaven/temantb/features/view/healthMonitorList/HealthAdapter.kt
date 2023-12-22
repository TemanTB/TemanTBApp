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
import com.bumptech.glide.Glide
import com.heaven.temantb.R
import com.heaven.temantb.databinding.ItemRowHealthBinding
import com.heaven.temantb.features.data.pref.retrofit.response.ListHealthItem
import com.heaven.temantb.features.view.healthMonitorDetail.HealthMonitorDetailActivity
import com.heaven.temantb.features.view.medicineScheduleDetail.MedicineScheduleDetailActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Suppress("DEPRECATION")
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

        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val date: Date? = try {
            inputDateFormat.parse(health.date)
        } catch (e: Exception) {
            null
        }

        val formattedDate: String = date?.let { outputDateFormat.format(it) } ?: ""

        holder.binding.apply {
            val descriptionText = health.description.let {
                if (it.length > 24) {
                    "${it.substring(0, 24)}..."
                } else {
                    it
                }
            }

            tvItemDescription.text = descriptionText
            tvItemDate.text = formattedDate
            tvItemAverage.text = health.average
            Glide.with(cardView.context)
                .load(health.images)
                .into(ivFlag)

            if (position == 0) {
                cardView.setBackgroundResource(R.drawable.blue_frame_with_border) // Set the blue frame with border drawable
            } else {
                cardView.setBackgroundResource(R.color.white) // Set the background to white for other items
            }

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
            val healthId = listOfHealth[position].healthId
            viewModel.deleteHealth(token, healthId)
            Log.d("Delete Schedule", "Token: $token, ScheduleId: $healthId")

            removeItem(position)
        }
    }
}