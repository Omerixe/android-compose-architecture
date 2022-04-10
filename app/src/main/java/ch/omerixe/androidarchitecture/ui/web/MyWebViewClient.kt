package ch.omerixe.androidarchitecture.ui.web

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MyWebViewClient : WebViewClient() {
    var backEnabled by mutableStateOf(false)
        private set

    companion object {
        val TAG = "MyWebViewClient"
    }

    init {
        Log.d(TAG, "newClient")
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Log.d(TAG, "shouldOverrideUrlLoading " + request?.getUrl().toString())
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        Log.d(TAG, "onPageFinished " + url)
        backEnabled = view?.canGoBack() ?: false
        super.onPageFinished(view, url)
    }
}