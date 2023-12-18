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
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.heaven.temantb.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("AlarmReceiver7", "onReceive called")
        val type = intent.getStringExtra(EXTRA_TYPE)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)

        Log.d("AlarmReceiver6", "Received alarm intent. Description: $description")

        val medicineName = TYPE_TEMANTB
        val notifId = ID_REPEATING


        if (description != null) {
            Log.d("AlarmReceiver5", "description not null")
            showAlarmNotification(context, medicineName, description, notifId)
        }
    }

//    fun setRepeatingAlarm(context: Context, token: String, hour: String, description: String) {
    fun setRepeatingAlarm(context: Context, type: String, hour: String, description: String) {
        Log.d("AlarmReceiver1", "showAlarmNotification called")
        if (checkPermission(context)){
            if (isDateInvalid(hour, TIME_FORMAT)) return

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.putExtra(EXTRA_DESCRIPTION, description)
            val timeArray = hour.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
                set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
                set(Calendar.SECOND, 0)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                ID_REPEATING,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )

            Toast.makeText(context, "Medicine alarm set up.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isDateInvalid(date: String, format: String): Boolean {
        Log.d("AlarmReceiver2", "date calid tak")

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
        Log.d("AlarmReceiver3", "done check?")
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = Manifest.permission.WAKE_LOCK
            val granted = PackageManager.PERMISSION_GRANTED
            ContextCompat.checkSelfPermission(context, permission) == granted
        } else {
            true
        }
    }

//    DAH BENER
    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, PendingIntent.FLAG_IMMUTABLE)
        if (pendingIntent != null) {
            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)
            Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show()
        }
    }


//    MEMBUAT NOTIFIKASI
    private fun showAlarmNotification(context: Context, medicineName: String, description: String, notifId: Int) {
    Log.d("AlarmReceiver4", "show alarmnotif ni kepanggil ga")
        val channelId = "Channel_1"
        val channelName = "AlarmManager channel"
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(medicineName)
            .setContentText(description)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    companion object {
        const val TYPE_TEMANTB = "TemanTB"
        const val EXTRA_DESCRIPTION = "description"
        const val EXTRA_TYPE = "type"
        private const val ID_REPEATING = 101
        private const val TIME_FORMAT = "HH:mm"
    }
}
