package com.heaven.temantb.features.setting

import android.os.Bundle
import android.preference.ListPreference
import android.preference.SwitchPreference
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.heaven.temanTB.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : DONE Update theme based on value in ListPreference
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

        //TODO 11 : DONE Schedule and cancel notification in DailyReminder based on SwitchPreference
        val notificationPreferences = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        val dailyReminder = DailyReminder()

        notificationPreferences?.setOnPreferenceChangeListener { _, switchValue ->
            val inputtedSwitch = switchValue as Boolean

            if (inputtedSwitch) {
                scheduleNotification(dailyReminder)
            } else {
                cancelNotification(dailyReminder)
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
    }

    private fun scheduleNotification(dailyReminder: DailyReminder) {
        dailyReminder.setDailyReminder(requireContext())
    }

    private fun cancelNotification(dailyReminder: DailyReminder) {
        dailyReminder.cancelAlarm(requireContext())
    }
}
