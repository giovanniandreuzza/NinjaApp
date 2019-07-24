package com.github.giovanniandreuzza.bokuwanarutodesu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.giovanniandreuzza.data.GetNinjaUseCase

class NinjaViewModelFactory(private val getNinjaUseCase: GetNinjaUseCase) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(getNinjaUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}