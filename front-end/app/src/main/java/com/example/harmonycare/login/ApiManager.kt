package com.example.harmonycare.login
import com.google.gson.annotations.SerializedName
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/api/v1/auth/login")
    fun loginUser(@Body requestBody: RequestBody): Call<ApiResponse>
}

class ApiManager(private val apiService: ApiService) {

    fun loginUser(authCode: String, onResponse: (accessToken: String) -> Unit, onFailure: () -> Unit) {
        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), "{\"authcode\": \"$authCode\"}")
        val call = apiService.loginUser(requestBody)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val accessToken = response.body()?.response?.token?.accessToken
                    if (accessToken != null) {
                        onResponse(accessToken)
                        return
                    }
                }
                onFailure()
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onFailure()
            }
        })
    }
}

// API 응답을 모델링할 클래스 정의
data class ApiResponse(
    val status: Int,
    val response: ResponseData
)

data class ResponseData(
    val isFirstLogin: Boolean,
    val token: TokenData
)

data class TokenData(
    val grantType: String,
    @SerializedName("accessToken") val accessToken: String
)
