package ch.omerixe.androidarchitecture

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
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