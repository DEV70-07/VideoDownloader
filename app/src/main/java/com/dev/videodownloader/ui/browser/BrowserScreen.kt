package com.dev.videodownloader.ui.browser

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun BrowserScreen(
    modifier: Modifier,
    url: String,
    loadProgress: (Int) -> Unit,
    urlUpdate: (String) -> Unit,
    pageTitleUpdate: (String) -> Unit,
    iconUpdate: (Bitmap?) -> Unit,
    setTopBarVisibility: (Boolean) -> Unit
) {
    AndroidView(
        modifier = modifier,
        factory = {
            WebView(it).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        if (url != null){
                            urlUpdate(url)
                        }
                        view?.evaluateJavascript(
                            "console.log(\"Yeah\")", null
                        )
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        loadProgress(newProgress)
                    }

                    override fun onReceivedTitle(view: WebView?, title: String?) {
                        super.onReceivedTitle(view, title)
                        if (title != null){
                            pageTitleUpdate(title)
                        } else {
                            pageTitleUpdate("")
                        }
                    }

                    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                        super.onReceivedIcon(view, icon)
                        if (icon != null) {
                            iconUpdate(icon)
                        }
                    }


                }

                setOnScrollChangeListener { _, _, actualScroll, _, oldScroll ->
                    if (actualScroll < oldScroll && actualScroll < 50) {
                        setTopBarVisibility(true)
                    } else {
                        setTopBarVisibility(false)
                    }
                }


            }
        },
        update = {
            if (it.url != url) {
                it.loadUrl(url)
            }
        }
    )
}