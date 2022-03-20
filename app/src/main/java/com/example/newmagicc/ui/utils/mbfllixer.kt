package com.example.newmagicc.ui.utils

import android.app.ProgressDialog
import android.webkit.WebView
import android.webkit.WebViewClient

class mbfllixer(var pd: ProgressDialog) : WebViewClient() {

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        if (pd.isShowing) {
            pd.dismiss()
        }
    }

    init {
        pd.show()
    }

}