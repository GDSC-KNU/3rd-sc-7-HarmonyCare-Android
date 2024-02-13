package com.example.harmonycare

import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.credentials.GetCredentialException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.CustomCredential
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import java.util.logging.Handler

class LoginActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    val credentialManager= androidx.credentials.CredentialManager.create(this)
    val getPasswordOption = GetPasswordOption()
    val googleOAuthUrl = URL("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile&access_type=offline&include_granted_scopes=true&response_type=code&redirect_uri=http://localhost:8080&client_id=185976520158-phphtutm302clototd3rqgecng4a4bg2.apps.googleusercontent.com\")\n")

    /*// Get passkey from the user's public key credential provider.
    val getPublicKeyCredentialOption = GetPublicKeyCredentialOption(
        requestJson = requestJson
    )
    val getCredRequest = GetCredentialRequest(
        listOf(getPasswordOption, getPublicKeyCredentialOption)
    )


    val am: AccountManager = AccountManager.get(this)
    val options = Bundle()

    fun getToken() {
        val options = null // Authenticator-specific options

        am.getAuthToken(
            googleOAuthUrl,                     // Account retrieved using getAccountsByType()
            "Manage your tasks",            // Auth scope
            options,                        // Authenticator-specific options
            this,                           // Your activity
            OnTokenAcquired(),              // Callback called when a token is successfully acquired
            Handler(OnError())              // Callback called if an error occurs
        )
    }

    private inner class OnTokenAcquired : AccountManagerCallback<Bundle> {

        override fun run(result: AccountManagerFuture<Bundle>) {
            val launch: Intent? = result.getResult().get(AccountManager.KEY_INTENT) as? Intent
            if (launch != null) {
                startActivityForResult(launch, 0)
            }
        }
    }

    private inner class OnError : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            // Handle error here
            Log.e(TAG, "Error occurred during authentication")
            return true
        }
    }*/

    fun sendRequest(token: String) {
        val url = URL("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile&access_type=offline&include_granted_scopes=true&response_type=code&redirect_uri=http://localhost:8080&client_id=185976520158-phphtutm302clototd3rqgecng4a4bg2.apps.googleusercontent.com")
        val conn = url.openConnection() as HttpURLConnection

        conn.apply {
            addRequestProperty("client_id", "your_client_id")
            addRequestProperty("client_secret", "your_client_secret")
            setRequestProperty("Authorization", "OAuth $token")
        }
    }


    /*private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private val REQ_ONE_TAP = 200
    private var showOneTapUI = true*/
    /*val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(SERVER_CLIENT_ID)
        .build()*/


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {

        /*oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                .setSupported(true)
                .build())
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.google_server_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)//true를 하면 인증계정으로만 필터링함
                    .build())
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build()*/
        /*val loginButton: ImageButton = findViewById(R.id.GoogleLoginButton)
                loginButton.setOnClickListener {
                    showLoginDialog()
                }*/

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        webView = findViewById(R.id.webView)

        // WebView 설정
        webView.webViewClient = WebViewClient()
        webView.settings.userAgentString = "Mozilla/5.0 AppleWebKit/535.19 Chrome/121.0.0 Mobile Safari/535.19";
        webView.settings.javaScriptEnabled = true

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

    // 자격 증명을 가져오는 과정에서 예외가 발생했을 때의 처리를 담은 함수를 정의합니다.
    private fun handleFailure(e: GetCredentialException) {
        // GetCredentialException 예외를 처리하는 코드를 작성합니다.
        Log.e(TAG, "Error retrieving credential: ${e.message}")
    }

    companion object {
        private const val TAG = "LoginActivity"
    }


}

