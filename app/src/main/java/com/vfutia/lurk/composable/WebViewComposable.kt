package com.vfutia.lurk.composable

import android.graphics.Bitmap
import android.view.ViewGroup.LayoutParams
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewComposable(
    modifier: Modifier = Modifier,
    url: String?
) {
    var isLoadingPage = remember { mutableStateOf(true) }

    Box (
        modifier = modifier,
    ) {
        AndroidView(
            modifier = Modifier,
            factory = {
                WebView(it).apply {
                    layoutParams = LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT
                    )

                    webViewClient = object: WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            isLoadingPage.value = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoadingPage.value = false
                        }
                    }

                    webChromeClient = CustomWebChromeClient()

                    settings.apply {
                        setSupportZoom(true)
                        builtInZoomControls = true
                        displayZoomControls = false
                        javaScriptEnabled = true
                        useWideViewPort = true
                    }
                }
            }, update = { view ->
                url?.let { view.loadUrl(it) }
            })

        if (isLoadingPage.value) {
            Loader(modifier = Modifier.align(Alignment.Center))
        }
    }
}


class CustomWebChromeClient: WebChromeClient() {

}