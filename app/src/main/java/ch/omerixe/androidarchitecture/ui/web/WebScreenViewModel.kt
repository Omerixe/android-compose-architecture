package ch.omerixe.androidarchitecture.ui.web

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebScreenViewModel @Inject constructor(
    val webViewClient: MyWebViewClient
) : ViewModel() {
    var url: String? = null
}