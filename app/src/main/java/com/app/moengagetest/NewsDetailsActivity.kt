package com.app.moengagetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import com.app.moengagetest.databinding.ActivityNewsDetailsBinding
import kotlinx.android.synthetic.main.activity_news_details.*

class NewsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailsBinding
    private var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_details)

        url = intent.getStringExtra("url").toString()

        /**
         * Setting a webViewClient
         * and load the url
         */
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(url)
    }

    /**
     * Overriding WebViewClient functions.
     * Load the url
     * ProgressBar will invisible once page is loaded
     */
    inner class WebViewClient : android.webkit.WebViewClient() {

        // Load the URL
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        // ProgressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.progressBar.visibility = View.GONE
        }
    }
    /**
     *  if you press Back button this code will work
     *  if your Review cannot go back
     *  it will exit the application
     */

    override fun onBackPressed() {
        if (webView.canGoBack())
            webView.goBack()
        else
            super.onBackPressed()
    }
}