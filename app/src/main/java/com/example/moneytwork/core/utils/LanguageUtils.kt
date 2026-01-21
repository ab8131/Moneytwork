package com.example.moneytwork.core.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.Locale

object LanguageUtils {
  fun setAppLanguage(context: Context, languageCode: String) {
    val locale = Locale(languageCode)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      // Android 13+
      context.getSystemService(LocaleManager::class.java)?.setApplicationLocales(LocaleList(locale))
    } else {
      // Older Android versions
      Locale.setDefault(locale)
      val config = context.resources.configuration
      config.setLocale(locale)
      context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
  }

  fun getAppLanguage(context: Context): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      val locales = context.getSystemService(LocaleManager::class.java)?.applicationLocales
      locales?.get(0)?.language ?: "en"
    } else {
      Locale.getDefault().language
    }
  }
}
