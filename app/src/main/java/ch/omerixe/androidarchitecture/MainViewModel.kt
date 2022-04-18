package ch.omerixe.androidarchitecture

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var loggedIn = mutableStateOf(false)
        private set

    init {
        println("VM initialized")
    }

    fun logIn() {
        loggedIn.value = true
    }

    fun logOut() {
        loggedIn.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }
}