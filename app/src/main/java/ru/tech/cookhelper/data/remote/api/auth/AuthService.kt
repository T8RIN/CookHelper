package ru.tech.cookhelper.data.remote.api.auth

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @Headers("Content-Type: application/json")
    @GET("api/user/post/auth/")
    fun loginWith(
        @Query("nickname") nickname: String = "",
        @Query("email") email: String = "",
        @Query("password") password: String
    ): Call<AuthInfo>

}

fun <T> Call<T>.start(
    onSuccess: (call: Call<T>, response: Response<T>) -> Unit,
    onError: (call: Call<T>, t: Throwable) -> Unit,
) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) = onSuccess(call, response)
        override fun onFailure(call: Call<T>, t: Throwable) = onError(call, t)
    })
}
