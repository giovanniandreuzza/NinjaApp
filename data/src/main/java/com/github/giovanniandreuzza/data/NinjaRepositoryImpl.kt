package com.github.giovanniandreuzza.data

import io.reactivex.Single
import okhttp3.ResponseBody

class NinjaRepositoryImpl(restClient: RestClient) :
    NinjaRepository {

    private val ninjaEndPoint = restClient.prepareRequest(NinjaEndPoint::class.java)

    override fun getNinja(ep: Int): Single<ResponseBody> {
        return ninjaEndPoint.getNinja("${ep}_SUB_ITA.mp4") as Single<ResponseBody>
    }

}