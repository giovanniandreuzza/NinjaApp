package com.github.giovanniandreuzza.bokuwanarutodesu

import androidx.lifecycle.ViewModel
import com.github.giovanniandreuzza.data.UseCase

abstract class BaseViewModel(vararg useCases: UseCase) : ViewModel() {

    private var useCaseList: MutableList<UseCase> = mutableListOf()

    init {
        useCaseList.addAll(useCases)
    }

    override fun onCleared() {
        super.onCleared()
        useCaseList.forEach { it.dispose() }
    }

}