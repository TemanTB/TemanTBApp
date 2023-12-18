package com.heaven.temantb.features.view.setting

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.heaven.temantb.R
import com.heaven.temantb.features.alarm.AlarmReceiver
import com.heaven.temantb.util.AppPreferences

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val notificationSwitchPreference = findPreference<SwitchPreferenceCompat>("pref_key_notify")

        notificationSwitchPreference?.setOnPreferenceChangeListener { _, newValue ->
            val notificationEnabled = newValue as Boolean
            if(notificationEnabled){
                alarmReceiver.setRepeatingAlarm(requireContext(), EXTRA_TOKEN)
            } else{
                alarmReceiver.cancelAlarm(requireContext())
            }
//            saveNotificationSetting(notificationEnabled)
            true
        }

        val themePreferences = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreferences?.setOnPreferenceChangeListener { _, newValue ->
            val nightTheme = when (newValue.toString()) {
                getString(R.string.pref_dark_off) -> AppCompatDelegate.MODE_NIGHT_NO
                getString(R.string.pref_dark_on) -> AppCompatDelegate.MODE_NIGHT_YES
                getString(R.string.pref_dark_auto) -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            updateTheme(nightTheme)
            true
        }
    }

//    private fun saveNotificationPreferenceStatus(isEnabled: Boolean) {
//        // Save the notification preference status to SharedPreferences
//        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
//        sharedPreferences.edit().putBoolean("notification_enabled", isEnabled).apply()
//    }

    private fun updateTheme(nightMode: Int) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
    }

//    private fun saveNotificationSetting(enabled: Boolean) {
//        val sharedPreferences =
//            requireActivity().getSharedPreferences(AppPreferences.PREF_NAME, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putBoolean(AppPreferences.PREF_KEY_NOTIFICATION_ENABLED, enabled)
//        editor.apply()
//    }
    companion object{
        private const val EXTRA_TOKEN = "extra_token"
    }
}