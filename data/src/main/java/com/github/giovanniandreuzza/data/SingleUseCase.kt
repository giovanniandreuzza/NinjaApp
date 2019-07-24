package com.github.giovanniandreuzza.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T, in Params> : UseCase() {

    internal abstract fun buildUseCaseSingle(params: Params): Single<T>

    fun execute(
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {},
        params: Params
    ) {
        disposeLast()
        lastDisposable = buildUseCaseSingle(params)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, onError)
        lastDisposable?.let { compositeDisposable.add(it) }
    }
}

abstract class SingleAuthorizationUseCase<T, in Params> : UseCase() {

    internal abstract fun buildUseCaseSingle(authorizationToken: String, params: Params): Single<T>

    fun execute(
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {},
        authorizationToken: String,
        params: Params
    ) {
        disposeLast()

        lastDisposable = buildUseCaseSingle(authorizationToken, params)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, onError)
        lastDisposable?.let { compositeDisposable.add(it) }
    }
}