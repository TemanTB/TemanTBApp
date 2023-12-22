package com.heaven.temantb.features.alarm

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.heaven.temantb.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val medicineName = intent.getStringExtra(EXTRA_MEDICINE_NAME) ?: TYPE_TEMANTB


        val notifId = generateUniqueId()

        if (description != null) {
            showAlarmNotification(context, medicineName, description, getCurrentTime(), notifId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun setRepeatingAlarm(context: Context, type: String, hour: String, description: String?, medicineName: String) {
        if (checkPermission(context)) {
            if (isDateInvalid(hour, TIME_FORMAT)) return

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)

            val defaultDescription =
                context.getString(R.string.it_s_time_to_take_your_medicine_please_get_it_now)
            val alarmDescription = description.takeIf { !it.isNullOrBlank() } ?: defaultDescription

            intent.putExtra(EXTRA_DESCRIPTION, alarmDescription)
            intent.putExtra(EXTRA_MEDICINE_NAME, medicineName)
            val timeArray = hour.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
                set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
                set(Calendar.SECOND, 0)
            }

            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                generateUniqueId(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.cannot_schedule_exact_alarms_on_this_device),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }

            Toast.makeText(context,
                context.getString(R.string.medicine_alarm_set_up), Toast.LENGTH_SHORT).show()
        }
    }

    private fun isDateInvalid(date: String, format: String): Boolean {
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }
    }

    private fun checkPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = Manifest.permission.WAKE_LOCK
            val granted = PackageManager.PERMISSION_GRANTED
            ContextCompat.checkSelfPermission(context, permission) == granted
        } else {
            true
        }
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                generateUniqueId(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        if (pendingIntent != null) {
            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)
            Toast.makeText(context,
                context.getString(R.string.repeating_alarm_cancelled), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAlarmNotification(
        context: Context,
        medicineName: String,
        description: String,
        hour: String,
        notifId: Int
    ) {
        val channelId = "Channel_1"
        val channelName = "AlarmManager channel"
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val alarmSound = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.heal)

//        val intent = Intent(context, MedicineScheduleDetailActivity::class.java)
//
//        intent.putExtra("EXTRA_MEDICINE_NAME", medicineName)
//        intent.putExtra("EXTRA_DESCRIPTION", description)

//        val pendingIntent = PendingIntent.getActivity(
//            context,
//            notifId,
////            intent,
//            PendingIntent.FLAG_IMMUTABLE
//        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_ttb_circle)
            .setContentTitle(medicineName)
            .setContentText(description)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 2000, 2000, 2000, 1000))
            .setSound(alarmSound)
//            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.cancel(notifId)

        notificationManagerCompat.notify(notifId, notification)
    }

    private fun generateUniqueId(): Int {
        return System.currentTimeMillis().toInt()
    }

    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
    }

    companion object {
        const val TYPE_TEMANTB = "TemanTB"
        const val EXTRA_DESCRIPTION = "description"
        const val EXTRA_MEDICINE_NAME = "medicineName"
        const val EXTRA_TYPE = "type"
        private const val TIME_FORMAT = "HH:mm"
    }
}
