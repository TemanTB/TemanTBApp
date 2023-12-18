//    package com.heaven.temantb.features.alarm
//
//    import android.content.BroadcastReceiver
//    import android.content.Context
//    import android.content.Intent
//    import androidx.core.app.NotificationCompat
//    import androidx.core.app.NotificationManagerCompat
//    import com.heaven.temantb.R
//
//    class AlarmReceiver : BroadcastReceiver() {
//
//        override fun onReceive(context: Context, intent: Intent) {
//            val medicineName = intent.getStringExtra("medicineName")
//            val description = intent.getStringExtra("description")
//
//            showNotification(context, medicineName, description)
//        }
//
//        private fun showNotification(context: Context, medicineName: String?, description: String?) {
//            val notificationId = System.currentTimeMillis().toInt()
//
//            val builder = NotificationCompat.Builder(context, "channel_id")
//                .setSmallIcon(R.drawable.ic_notification)
//                .setContentTitle("Waktunya Minum Obat: $medicineName")
//                .setContentText(description)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//
//            val notificationManager = NotificationManagerCompat.from(context)
//            notificationManager.notify(notificationId, builder.build())
//        }
//    }
