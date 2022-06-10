package ru.tech.cookhelper.data.remote.api.auth

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import ru.tech.cookhelper.data.remote.body.LoginBody

interface AuthService {

    @Headers("Content-Type: application/json")
    @POST("api/user/post/auth/")
    fun loginWith(
        @Body loginBody: LoginBody
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
