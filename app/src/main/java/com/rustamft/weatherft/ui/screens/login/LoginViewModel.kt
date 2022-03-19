package com.rustamft.weatherft.ui.screens.login

import androidx.lifecycle.ViewModel
import com.rustamft.weatherft.database.prefs.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val prefs: SharedPrefs
) : ViewModel() {

    val listOfCities = mutableListOf<String>()

    fun findCity(city: String) {

    }
}