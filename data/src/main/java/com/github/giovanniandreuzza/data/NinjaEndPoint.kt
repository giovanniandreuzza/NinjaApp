package com.github.giovanniandreuzza.data

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface NinjaEndPoint {

    @Streaming
    @GET("Naruto_Ep_00{ep}")
    fun getNinja(@Path("ep") ep: String): Single<ResponseBody>

}