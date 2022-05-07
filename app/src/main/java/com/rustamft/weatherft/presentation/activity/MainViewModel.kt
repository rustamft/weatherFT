package com.rustamft.weatherft.presentation.activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rustamft.weatherft.domain.model.AppPreferences
import com.rustamft.weatherft.domain.usecase.GetAppPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAppPreferencesUseCase: GetAppPreferencesUseCase
) : ViewModel() {

    private val appPreferencesFlow = getAppPreferencesUseCase.execute()
    private val _appPreferencesState = mutableStateOf(AppPreferences())
    val appPreferencesState get() = _appPreferencesState as State<AppPreferences>

    init {
        viewModelScope.launch {
            appPreferencesFlow.collect {
                _appPreferencesState.value = it
            }
        }
    }
}
