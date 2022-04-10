package ch.omerixe.androidarchitecture.ui.web

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen(url: String) {
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = MyWebViewClient { backEnabled = it }
                settings.javaScriptEnabled = true
                loadUrl(url)
                webView = this
            }
        },
        update = { newWebView ->
            webView = newWebView
        }
    )

    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }
}

