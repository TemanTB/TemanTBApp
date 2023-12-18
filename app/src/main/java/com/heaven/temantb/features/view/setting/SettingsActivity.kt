package com.heaven.temantb.features.view.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.heaven.temantb.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
//class SettingsActivity : AppCompatActivity() {
//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                showToast("Notifications permission granted")
//            } else {
//                showToast("Notifications will not show without permission")
//            }
//        }
//
//    private fun showToast(msg: String) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.settings_activity)
//
//        if (savedInstanceState == null) {
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.settings, SettingsFragment())
//                .commit()
//        }
//    }
//
//    companion object {
//        private const val EXTRA_TOKEN = "extra_token"
//
//        // Use this method to create an Intent with the token
//        fun newIntent(context: Context, token: String): Intent {
//            return Intent(context, SettingsActivity::class.java).apply {
//                putExtra(EXTRA_TOKEN, token)
//            }
//        }
//    }
//}
