package com.example.harmonycare.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.harmonycare.data.Record
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/api/v1/auth/login")
    fun loginUser(@Body requestBody: RequestBody): Call<ApiResponse>

    @POST("/api/v1/record")
    fun saveRecord(
        @Header("Authorization") authToken: String,
        @Body requestBody: RecordSaveRequest
    ): Call<SaveRecordResponse>

    @GET("/api/v1/record")
    fun getRecordsForDay(
        @Query("day") day: String,
        @Query("range") range: Int,
        @Header("Authorization") authToken: String
    ): Call<RecordGetResponse>

    @PUT("/api/v1/record/{recordId}")
    fun updateRecord(
        @Path("recordId") recordId: Int,
        @Header("Authorization") authToken: String,
        @Body requestBody: RecordSaveRequest
    ): Call<SaveRecordResponse>

    @DELETE("/api/v1/record/{recordId}")
    fun deleteRecord(
        @Path("recordId") recordId: Int,
        @Header("Authorization") authToken: String,
    ): Call<RecordDeleteResponse>
}

class ApiManager(private val apiService: ApiService) {

    fun loginUser(authCode: String, onResponse: (accessToken: String) -> Unit, onFailure: () -> Unit) {
        val requestBody = "{\"authcode\": \"$authCode\"}".toRequestBody("application/json".toMediaTypeOrNull())
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

    fun saveRecord(
        accessToken: String,
        recordTask: String,
        startTime: String,
        endTime: String,
        description: String,
        onResponse: (Int) -> Unit,
        onFailure: () -> Unit
    ) {
        val recordRequest = RecordSaveRequest(recordTask, startTime, endTime, description)
        val call = apiService.saveRecord("Bearer $accessToken", recordRequest)
        call.enqueue(object : Callback<SaveRecordResponse> {
            override fun onResponse(call: Call<SaveRecordResponse>, response: Response<SaveRecordResponse>) {
                if (response.isSuccessful) {
                    val saveRecordResponse = response.body()
                    if (saveRecordResponse != null) {
                        onResponse(saveRecordResponse.status)
                        return
                    }
                }
                onFailure()
            }

            override fun onFailure(call: Call<SaveRecordResponse>, t: Throwable) {
                onFailure()
            }
        })
    }

    fun getRecordsForDay(day: String, range: Int, authToken: String, onResponse: (List<Record>?) -> Unit, onFailure: (Throwable) -> Unit) {
        val call = apiService.getRecordsForDay(day, range, authToken)
        call.enqueue(object : Callback<RecordGetResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<RecordGetResponse>,
                response: Response<RecordGetResponse>
            ) {
                if (response.isSuccessful) {
                    val recordGetResponse: RecordGetResponse? = response.body()

                    onResponse(recordGetResponse?.response?.map { recordRequest ->
                        Record(
                            recordId = recordRequest.recordId,
                            recordTask = recordRequest.recordTask,
                            startTime = LocalDateTime.parse(recordRequest.startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                            endTime = LocalDateTime.parse(recordRequest.endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                            description = recordRequest.description
                        )
                    })
                } else {
                    onFailure(Throwable("Unsuccessful response: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<RecordGetResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun updateRecord(
        recordId: Int,
        recordTask: String,
        startTime: String,
        endTime: String,
        description: String,
        accessToken: String,
        onResponse: (Boolean) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val recordRequest = RecordSaveRequest(recordTask, startTime, endTime, description)
        val call = apiService.updateRecord(recordId, "Bearer $accessToken", recordRequest)
        call.enqueue(object : Callback<SaveRecordResponse> {
            override fun onResponse(
                call: Call<SaveRecordResponse>,
                response: Response<SaveRecordResponse>
            ) {
                if (response.isSuccessful) {
                    onResponse(true)
                } else {
                    onFailure(Throwable("Failed to update record: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<SaveRecordResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun deleteRecord(
        recordId: Int, accessToken: String, onResponse: (Boolean) -> Unit
    ) {
        val call = apiService.deleteRecord(recordId, "Bearer $accessToken")
        call.enqueue(object : Callback<RecordDeleteResponse> {
            override fun onResponse(
                call: Call<RecordDeleteResponse>,
                response: Response<RecordDeleteResponse>
            ) {
                if (response.isSuccessful) {
                    onResponse(true)
                }
            }

            override fun onFailure(call: Call<RecordDeleteResponse>, t: Throwable) {
                onResponse(false)
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

// 기록 저장하기
data class SaveRecordResponse(
    val status: Int,
    val response: Int
)

data class RecordSaveRequest(
    val recordTask: String,
    val startTime: String,
    val endTime: String,
    val description: String
)

// 기록 불러오기
data class RecordGetRequest(
    val recordId: Int,
    val recordTask: String,
    val startTime: String,
    val endTime: String,
    val description: String
)

data class RecordGetResponse(
    val status: Int,
    val response: List<RecordGetRequest>
)

data class RecordDeleteResponse(
    val status: Int
)