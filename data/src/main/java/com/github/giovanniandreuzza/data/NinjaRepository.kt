package com.github.giovanniandreuzza.data

import io.reactivex.Single
import okhttp3.ResponseBody

interface NinjaRepository {

    fun getNinja(ep: Int): Single<ResponseBody>

}