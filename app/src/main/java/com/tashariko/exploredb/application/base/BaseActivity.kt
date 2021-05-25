package com.tashariko.exploredb.application.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.util.SharedPreferenceHelper
import javax.inject.Inject

open abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(if (getCurrentMode()) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)

    }

    private  fun getCurrentMode(): Boolean {
        return SharedPreferenceHelper.getBooleanFromSharedPreference(this, AppConstants.SP_IS_LIGHT_THEME_KEY, true)
    }

    public fun showToast(msg: String) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

    fun showSnackbar(view: View) {
        Snackbar.make(view, getString(R.string.generic_error_message), Snackbar.LENGTH_SHORT).show()
    }

    fun closeWithError(msg:String) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

    abstract fun handleIncomingIntent()
}