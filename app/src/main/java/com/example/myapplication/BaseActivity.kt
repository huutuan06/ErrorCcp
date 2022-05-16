package com.example.myapplication

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import java.util.*

abstract class BaseActivity: AppCompatActivity() {
    private var defaultLanguage: String = Locale.getDefault().language

    fun restartActivity(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate()
        } else {
            activity.finish()
            activity.startActivity(activity.intent)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        onAttach(newBase)
        super.attachBaseContext(newBase)
    }

    fun onAttach(context: Context, defaultLanguage: String = Locale.getDefault().language): Context {
        this.defaultLanguage = defaultLanguage
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun setLocale(context: Context, language: String): Context {
        persist(context, language)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language)
        } else {
            return updateResourcesLegacy(context, language)
        }
    }

    private fun getPersistedData(context: Context, defaultLanguage: String): String {
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString("Locale.Helper.Selected.Language", defaultLanguage) ?: defaultLanguage
    }

    private fun persist(context: Context, language: String) {
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString("Locale.Helper.Selected.Language", language)
        editor.apply()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    @Suppress("DEPRECATION")
    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}