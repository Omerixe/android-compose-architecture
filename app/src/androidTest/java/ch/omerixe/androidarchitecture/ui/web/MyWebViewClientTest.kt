package ch.omerixe.androidarchitecture.ui.web

import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class MyWebViewClientTest2 {
    private lateinit var webView: TestWebView

    @Before
    fun setUp() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            webView = TestWebView(ApplicationProvider.getApplicationContext())
        }
    }

    @Test
    fun testBackEndabled() {
        val client = MyWebViewClient()
        webView.canGoBack = true
        client.onPageFinished(webView, "")
        assertEquals(true, client.backEnabled)
    }

    @Test
    fun testBackNotEndabled() {
        val client = MyWebViewClient()
        webView.canGoBack = false
        client.onPageFinished(webView, "")
        assertEquals(false, client.backEnabled)
    }
}