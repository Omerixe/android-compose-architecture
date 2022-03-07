package ch.omerixe.androidarchitecture.ui

import androidx.lifecycle.ViewModel

class DetailScreenViewModel : ViewModel() {

    init {
        println("VM initialized")
    }

    override fun onCleared() {
        super.onCleared()
    }
}