package ch.omerixe.androidarchitecture.ui.web

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen(viewModel: WebScreenViewModel) {
    val backEnabled = viewModel.webViewClient.backEnabled
    var webView: WebView? = null

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = viewModel.webViewClient
                settings.javaScriptEnabled = true
                loadUrl(viewModel.url ?: "https://www.google.ch")
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

