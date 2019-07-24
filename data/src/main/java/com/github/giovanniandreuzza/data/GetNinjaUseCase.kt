package com.github.giovanniandreuzza.data

import io.reactivex.Single
import okhttp3.ResponseBody

class GetNinjaUseCase(private val ninjaRepository: NinjaRepository) :
    SingleUseCase<ResponseBody, Int>() {

    override fun buildUseCaseSingle(params: Int): Single<ResponseBody> {
        return ninjaRepository.getNinja(params)
    }

}