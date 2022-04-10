package ch.omerixe.androidarchitecture.ui.web

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WebScreenViewModel(
    val webViewClient: MyWebViewClient,
    val url: String
) : ViewModel()

class WebScreenViewModelFactory(val webViewClient: MyWebViewClient, val url: String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MyWebViewClient::class.java, String::class.java)
            .newInstance(webViewClient, url)
    }
}