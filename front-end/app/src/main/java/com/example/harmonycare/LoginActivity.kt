package com.example.harmonycare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        webView = findViewById(R.id.webView)

        // WebView 설정
        webView.settings.userAgentString = "Mozilla/5.0 AppleWebKit/535.19 Chrome/121.0.0 Mobile Safari/535.19"
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null && url.startsWith("http://localhost:8080")) {
                    // 리다이렉션 링크를 가져와서 파라미터 추출
                    showToast("리다이렉션 링크: $url")
                    val authCode = extractCodeFromUrl(url)
                    if (authCode != null) {
                        // SharedPreferences에 authcode 저장
                        saveAuthCode(authCode)
                        showToast("인증 코드 저장됨: $authCode")

                        // MainActivity로 이동
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        // 현재 액티비티 종료
                        finish()
                    } else {
                        showToast("인증 코드가 없습니다.")
                    }
                    return true // 리다이렉션된 링크 처리를 위해 true 반환
                }
                // 다른 URL은 기본적인 방식으로 처리
                return super.shouldOverrideUrlLoading(view, url)
            }
        }

        // Google 로그인 페이지 URL 로드
        val url = "https://accounts.google.com/o/oauth2/v2/auth?" +
                "scope=https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile&" +
                "access_type=offline&" +
                "include_granted_scopes=true&" +
                "response_type=code&" +
                "redirect_uri=http://localhost:8080&" +
                "client_id=185976520158-phphtutm302clototd3rqgecng4a4bg2.apps.googleusercontent.com"
        webView.loadUrl(url)
    }

    private fun extractCodeFromUrl(url: String): String? {
        val pattern = Pattern.compile("\\bcode=([^&]+)")
        val matcher = pattern.matcher(url)

        return if (matcher.find()) {
            matcher.group(1)
        } else {
            null
        }
    }


    private fun saveAuthCode(authCode: String) {
        // SharedPreferences에 authcode 저장
        val editor = sharedPreferences.edit()
        editor.putString("authcode", authCode)
        editor.apply()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
