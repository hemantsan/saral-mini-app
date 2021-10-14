package com.deepsoftwares.saralonline

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkDownloadPermission();
        webView = findViewById(R.id.webview)
        webView.settings.domStorageEnabled = true;
        webView.settings.allowContentAccess = true;
        webView.settings.javaScriptEnabled = true;
        webView.settings.javaScriptCanOpenWindowsAutomatically = true;
        webView.addJavascriptInterface(JavaScriptInterface(applicationContext), "Android")

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                return true
            }
        }
        webView.loadUrl("https://deepsoftwares.com/saralonline_v2/sign-in/");

        webView.setDownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
            webView.loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(url))
        }
//        webView.loadUrl(R.string.app_domain.toString())
    }

    private fun checkDownloadPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this@MainActivity,
                "Write External Storage permission allows us to save files. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100
            )
        }
    }
}