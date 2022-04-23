package ch.omerixe.androidarchitecture.ui

import ch.omerixe.androidarchitecture.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {

    @Test
    fun testLogIn() {
        val vm = MainViewModel()
        vm.logIn()
        assertEquals(true, vm.loggedIn.value)
    }

    @Test
    fun testLogOut() {
        val vm = MainViewModel()
        vm.logOut()
        assertEquals(false, vm.loggedIn.value)
    }
}