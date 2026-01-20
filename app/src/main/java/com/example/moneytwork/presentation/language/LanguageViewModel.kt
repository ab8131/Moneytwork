package com.example.moneytwork.presentation.language

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytwork.core.utils.LanguageUtils
import com.example.moneytwork.data.preferences.LanguagePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class LanguageViewModel
@Inject
constructor(
        private val languagePreferences: LanguagePreferences,
        @ApplicationContext private val context: Context
) : ViewModel() {

    val selectedLanguage: StateFlow<String> =
            languagePreferences.selectedLanguage.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = "en"
            )

    private val _shouldRecreate = MutableStateFlow(false)
    val shouldRecreate: StateFlow<Boolean> = _shouldRecreate.asStateFlow()

    fun setLanguage(language: String) {
        viewModelScope.launch {
            languagePreferences.setLanguage(language)
            LanguageUtils.setAppLanguage(context, language)
            _shouldRecreate.value = true
        }
    }

    fun onRecreated() {
        _shouldRecreate.value = false
    }
}
