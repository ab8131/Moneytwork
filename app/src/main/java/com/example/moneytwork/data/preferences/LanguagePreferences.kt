package com.example.moneytwork.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by
        preferencesDataStore(name = "app_preferences")

@Singleton
class LanguagePreferences @Inject constructor(@ApplicationContext private val context: Context) {
    private val LANGUAGE_KEY = stringPreferencesKey("selected_language")

    // Support languages: en, fr, ha
    val selectedLanguage: Flow<String> =
            context.dataStore.data.map { preferences ->
                preferences[LANGUAGE_KEY] ?: "en" // Default to English
            }

    suspend fun setLanguage(language: String) {
        context.dataStore.edit { preferences -> preferences[LANGUAGE_KEY] = language }
    }
}
