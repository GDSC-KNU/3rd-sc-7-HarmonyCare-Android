package com.example.harmonycare.ui.login
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.harmonycare.R

class LoginDialog (
    context: Context,
    private val oauthUrl: String,
    private val callback: (String?) -> Unit // 콜백 함수
    ) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_login)

        // 로그인 페이지를 브라우저에서 열기
        openLoginPageInBrowser()

        // 뒤로가기 누를 때 취소 처리
        setOnCancelListener { callback(null) }
    }

    private fun openLoginPageInBrowser() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(oauthUrl))
        context.startActivity(intent)
    }
    }