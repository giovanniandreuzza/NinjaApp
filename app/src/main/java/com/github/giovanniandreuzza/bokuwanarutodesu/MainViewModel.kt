package com.github.giovanniandreuzza.bokuwanarutodesu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.giovanniandreuzza.data.GetNinjaUseCase
import com.github.giovanniandreuzza.data.NinjaResponse
import okhttp3.ResponseBody

class MainViewModel(private val getNinjaUseCase: GetNinjaUseCase) : BaseViewModel(getNinjaUseCase) {

    private var ninjaAlreadySaved = 1

    private val _getNinjaResponse = MutableLiveData<Result<Pair<ResponseBody, Int>>>()

    val getNinjaResponse: LiveData<Result<Pair<ResponseBody, Int>>>
        get() = _getNinjaResponse

    fun getNinja(numberOfEp: Int) {
        var ninjaCont = numberOfEp
        getNinjaUseCase.execute(
            onSuccess = { ninjaResponse ->
                ninjaAlreadySaved += 1
                ninjaCont -= 1
                if (ninjaCont > 0) {
                    getNinja(ninjaCont)
                }
                _getNinjaResponse.postValue(Result.success(Pair(ninjaResponse, ninjaAlreadySaved - 1)))
            },
            onError = { error ->
                _getNinjaResponse.postValue(Result.failure(error))
            },
            params = ninjaAlreadySaved
        )
    }

}