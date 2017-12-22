package br.com.dfn.starwarskotlin.core.model.source

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by diegonascimento on 18/12/17.
 */
interface FilmsDataSource<out T> {
    fun onFilmsLoaded(compositeDisposable: CompositeDisposable, success: (p: T) -> Unit, fail: () -> Unit)
}