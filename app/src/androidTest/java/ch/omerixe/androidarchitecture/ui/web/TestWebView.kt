package ch.omerixe.androidarchitecture.ui.web

import android.content.Context
import android.webkit.WebView

class TestWebView(context: Context) : WebView(context) {
    var canGoBack = false

    override fun canGoBack(): Boolean {
        return canGoBack
    }
}